package com.danil.kleshchin.rss.screens.imagezoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danil.kleshchin.rss.databinding.FragmentImageZoomBinding
import com.danil.kleshchin.rss.widgets.ZoomableImageView

class FeedImageZoomFragment : Fragment() {

    private var _binding: FragmentImageZoomBinding? = null
    private val binding get() = _binding!!
    private val args: FeedImageZoomFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageZoomBinding.inflate(inflater, container, false).also {
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
