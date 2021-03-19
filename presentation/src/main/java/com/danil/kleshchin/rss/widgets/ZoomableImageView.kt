package com.danil.kleshchin.rss.widgets

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView

//Copied from somewhere on the Internet
class ZoomableImageView : AppCompatImageView, View.OnTouchListener,
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    interface OnSingleTapConfirmedListener {
        fun onSingleTapConfirmed()
    }

    // Scales
    private val minScale = 1f
    private val maxScale = 25f
    private val initialScale = 1f
    private var currentScale = 1f
    private val emptyTranslation = 0f

    // view dimensions
    private var matrixScalesArraySize = 9
    private var originWidth = 0f
    private var originHeight = 0f
    private var viewWidth = 0
    private var viewHeight = 0
    private var lastPointF = PointF()
    private var startPointF = PointF()

    private var singleTapConfirmedListener: OnSingleTapConfirmedListener? = null

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var gestureDetector: GestureDetector

    private var matrixValuesArray = FloatArray(matrixScalesArraySize)
    private var scaleMatrix = Matrix()
    private var mode = NONE

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    )

    private fun init(context: Context) {
        super.setClickable(true)
        setOnTouchListener(this)
        scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
        gestureDetector = GestureDetector(context, this)
        imageMatrix = scaleMatrix
        scaleType = ScaleType.MATRIX
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (currentScale == initialScale) {
            fitToScreen()
        }
    }

    override fun onTouch(view: View?, event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        val currentPoint = PointF(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastPointF.set(currentPoint)
                startPointF.set(lastPointF)
                mode = DRAG
            }
            MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                val dx = currentPoint.x - lastPointF.x
                val dy = currentPoint.y - lastPointF.y
                val fixTransX = getFixDragTrans(dx, viewWidth.toFloat(), originWidth * currentScale)
                val fixTransY = getFixDragTrans(dy, viewHeight.toFloat(), originHeight * currentScale)
                scaleMatrix.postTranslate(fixTransX, fixTransY)
                fixTranslation()
                lastPointF[currentPoint.x] = currentPoint.y
            }
            MotionEvent.ACTION_POINTER_UP -> mode = NONE
        }
        imageMatrix = scaleMatrix
        return false
    }

    override fun onDown(motionEvent: MotionEvent) = false

    override fun onSingleTapUp(motionEvent: MotionEvent) = false

    override fun onShowPress(motionEvent: MotionEvent) = Unit

    override fun onLongPress(motionEvent: MotionEvent) = Unit

    override fun onScroll(
        motionEvent: MotionEvent,
        motionEvent1: MotionEvent,
        v: Float,
        v1: Float
    ) = false

    override fun onFling(
        motionEvent: MotionEvent,
        motionEvent1: MotionEvent,
        v: Float,
        v1: Float
    ) = false

    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {
        singleTapConfirmedListener?.onSingleTapConfirmed()
        return false
    }

    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        fitToScreen()
        return false
    }

    override fun onDoubleTapEvent(motionEvent: MotionEvent) = false

    fun setOnSingleTapConfirmedListener(listener: OnSingleTapConfirmedListener) {
        this.singleTapConfirmedListener = listener
    }

    private fun fitToScreen() {
        currentScale = initialScale
        val drawable = drawable
        val imageWidth = drawable.intrinsicWidth
        val imageHeight = drawable.intrinsicHeight
        if (drawable == null || imageWidth == 0 || imageHeight == 0) {
            return
        }
        val scaleX = viewWidth.toFloat() / imageWidth.toFloat()
        val scaleY = viewHeight.toFloat() / imageHeight.toFloat()
        val scale = scaleX.coerceAtMost(scaleY)
        scaleMatrix.setScale(scale, scale)

        // Center the image
        val redundantYSpace = (viewHeight.toFloat() - scale * imageHeight.toFloat()).div(2)
        val redundantXSpace = (viewWidth.toFloat() - scale * imageWidth.toFloat()).div(2)
        scaleMatrix.postTranslate(redundantXSpace, redundantYSpace)
        originWidth = viewWidth - redundantXSpace.times(2)
        originHeight = viewHeight - redundantYSpace.times(2)
        imageMatrix = scaleMatrix
    }

    private fun fixTranslation() {
        scaleMatrix.getValues(matrixValuesArray) //put matrix values into a float array so we can analyze
        val transX = matrixValuesArray[Matrix.MTRANS_X] //get the most recent translation in x direction
        val transY = matrixValuesArray[Matrix.MTRANS_Y] //get the most recent translation in y direction
        val fixTransX = getFixTranslation(transX, viewWidth.toFloat(), originWidth * currentScale)
        val fixTransY = getFixTranslation(transY, viewHeight.toFloat(), originHeight * currentScale)
        if (fixTransX != emptyTranslation || fixTransY != emptyTranslation) {
            scaleMatrix.postTranslate(fixTransX, fixTransY)
        }
    }

    private fun getFixTranslation(trans: Float, viewSize: Float, contentSize: Float): Float {
        val minTrans: Float
        val maxTrans: Float
        if (contentSize <= viewSize) { // case: NOT ZOOMED
            minTrans = emptyTranslation
            maxTrans = viewSize - contentSize
        } else { //CASE: ZOOMED
            minTrans = viewSize - contentSize
            maxTrans = emptyTranslation
        }
        if (trans < minTrans) { // negative x or y translation (down or to the right)
            return -trans + minTrans
        }
        if (trans > maxTrans) { // positive x or y translation (up or to the left)
            return -trans + maxTrans
        }
        return emptyTranslation
    }

    private fun getFixDragTrans(delta: Float, viewSize: Float, contentSize: Float): Float {
        return if (contentSize <= viewSize) {
            0F
        } else delta
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mode = ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var mScaleFactor = detector.scaleFactor
            val prevScale = currentScale
            currentScale *= mScaleFactor
            if (currentScale > maxScale) {
                currentScale = maxScale
                mScaleFactor = maxScale / prevScale
            } else if (currentScale < minScale) {
                currentScale = minScale
                mScaleFactor = minScale / prevScale
            }
            if (originWidth * currentScale <= viewWidth
                || originHeight * currentScale <= viewHeight) {
                scaleMatrix.postScale(
                    mScaleFactor, mScaleFactor,
                    viewWidth.div(2).toFloat(), viewHeight.div(2).toFloat()
                )
            } else {
                scaleMatrix.postScale(
                    mScaleFactor, mScaleFactor,
                    detector.focusX, detector.focusY
                )
            }
            fixTranslation()
            return true
        }
    }

    companion object {
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
    }
}
