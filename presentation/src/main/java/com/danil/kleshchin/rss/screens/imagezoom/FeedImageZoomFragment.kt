package com.danil.kleshchin.rss.screens.imagezoom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.danil.kleshchin.rss.databinding.FragmentImageZoomBinding
import com.danil.kleshchin.rss.widgets.ZoomableImageView
import com.squareup.picasso.Picasso

class FeedImageZoomFragment : Fragment() {

    private val HIDE_TOOLBAR_DELAY_MILIS = 120
    private var lastTouchTime = 0L

    private var _binding: FragmentImageZoomBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val KEY_IMAGE_URL = "KEY_IMAGE_URL"
        private val KEY_TOOLBAR_TITLE = "KEY_TOOLBAR_TITLE"

        fun newInstance(imageUrl: String, toolbarTitle: String): FeedImageZoomFragment {
            val feedFragment = FeedImageZoomFragment()
            val args = Bundle()
            args.putString(KEY_IMAGE_URL, imageUrl)
            args.putString(KEY_TOOLBAR_TITLE, toolbarTitle)
            feedFragment.arguments = args
            return feedFragment
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageZoomBinding.inflate(inflater, container, false)

        setBackPressedCallback()

        val imageUrl = arguments?.getString(KEY_IMAGE_URL)
        Picasso.get().load(imageUrl).into(binding.image)

        binding.feedTitle.text = arguments?.getString(KEY_TOOLBAR_TITLE)

        binding.image.setOnSingleTapConfirmedListener(object : ZoomableImageView.OnSingleTapConfirmedListener {
            override fun onSingleTapConfirmed() {
                setToolbarVisibility()
            }
        })
        binding.backButton.setOnClickListener { finish() }
        return binding.root
    }

    private fun setToolbarVisibility() {
        binding.feedToolbar.visibility = if (binding.feedToolbar.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    private fun setBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun finish() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commitNow()
    }
}
