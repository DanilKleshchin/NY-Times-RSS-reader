package com.danil.kleshchin.rss.screens.feedimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danil.kleshchin.rss.databinding.FragmentFeedImageBinding
import com.danil.kleshchin.rss.widgets.ZoomableImageView

class FeedImageFragment : Fragment() {

    private var _binding: FragmentFeedImageBinding? = null
    private val binding get() = _binding!!
    private val args: FeedImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedImageBinding.inflate(inflater, container, false).also {
            it.imageUrl = args.imageUrlArg
            it.toolbarTitle = args.toolbarTitleArg
            it.setClickListener { navigateBack() }
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
        binding.feedToolbar.isVisible = !binding.feedToolbar.isVisible
    }

    private fun setBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }
}
