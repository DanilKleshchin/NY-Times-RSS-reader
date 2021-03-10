package com.danil.kleshchin.rss.screens.imagezoom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danil.kleshchin.rss.databinding.FragmentImageZoomBinding
import com.danil.kleshchin.rss.widgets.ZoomableImageView

class FeedImageZoomFragment : Fragment() {

    private var _binding: FragmentImageZoomBinding? = null
    private val binding get() = _binding!!
    private val args: FeedImageZoomFragmentArgs by navArgs()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageZoomBinding.inflate(inflater, container, false).also {
            it.imageUrl = args.imageUrlArg
            it.toolbarTitle = args.toolbarTitleArg
            it.setClickListener { finish() }
            it.image.setOnSingleTapConfirmedListener(object :
                ZoomableImageView.OnSingleTapConfirmedListener {
                override fun onSingleTapConfirmed() {
                    setToolbarVisibility()
                }
            })
        }
        setBackPressedCallback()
        return binding.root
    }

    //TODO do this with animation
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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun finish() {
        findNavController().popBackStack()
    }
}
