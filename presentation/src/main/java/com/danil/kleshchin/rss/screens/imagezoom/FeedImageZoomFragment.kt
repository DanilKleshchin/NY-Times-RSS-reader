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
import com.squareup.picasso.Picasso

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
        _binding = FragmentImageZoomBinding.inflate(inflater, container, false)
        setBackPressedCallback()

        val imageUrl = args.imageUrlArg
        Picasso.get().load(imageUrl).into(binding.image)

        binding.feedTitle.text = args.toolbarTitleArg

        binding.image.setOnSingleTapConfirmedListener(object :
            ZoomableImageView.OnSingleTapConfirmedListener {
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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun finish() {
        findNavController().popBackStack()
    }
}
