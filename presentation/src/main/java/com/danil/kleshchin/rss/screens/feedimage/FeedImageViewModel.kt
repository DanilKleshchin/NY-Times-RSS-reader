package com.danil.kleshchin.rss.screens.feedimage

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.lang.ref.WeakReference


class FeedImageViewModel : ViewModel() {

    fun saveImageToGallery() {
        
    }

    internal class TargetToGallery(
        private val contentResolver: ContentResolver,
        private val name: String,
        private val desc: String
    ) : Target {

        private val resolver: WeakReference<ContentResolver> =
            WeakReference<ContentResolver>(contentResolver)

        override fun onPrepareLoad(arg0: Drawable?) = Unit

        override fun onBitmapLoaded(bitmap: Bitmap?, arg1: LoadedFrom?) {
            MediaStore.Images.Media.insertImage(resolver.get(), bitmap, name, desc)
        }

        override fun onBitmapFailed(e: Exception, arg0: Drawable?) {}

    }
}
