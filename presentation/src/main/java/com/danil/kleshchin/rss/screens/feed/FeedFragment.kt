package com.danil.kleshchin.rss.screens.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danil.kleshchin.rss.NYTimesRSSFeedsApp
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.FragmentFeedBinding
import com.danil.kleshchin.rss.entities.feed.FeedEntity

class FeedFragment : Fragment() {

    private val INSTANCE_STATE_PARAM_FEED = "STATE_PARAM_FEED"

    private val viewModel: FeedViewModel by viewModels()
    private val args: FeedFragmentArgs by navArgs()

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NYTimesRSSFeedsApp.INSTANCE.feedComponent.inject(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init(savedInstanceState)

        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackPressedCallback()

        binding.apply {
            this.viewModel = this@FeedFragment.viewModel
            pageUrl.setOnClickListener { showWebPage() }
            iconShare.setOnClickListener { createShareIntent() }
            backButton.setOnClickListener { navigateBack() }
            image.setOnClickListener { navigateToZoomImageScreen() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createShareIntent() {
        ShareCompat.IntentBuilder.from(requireActivity())
            .setType("text/plain")
            .setChooserTitle(getString(R.string.read_full_article))
            .setText(viewModel.feed.pageUrl)
            .startChooser()
    }

    private fun showWebPage() {
        //Todo use custom tabs for this
    }

    private fun navigateToZoomImageScreen() {
        viewModel.feed.apply {
            if (thumbUrl.isNotEmpty()) {
                val action = FeedFragmentDirections.actionFeedFragmentToFeedImageZoomFragment(
                    iconUrl,
                    title
                )
                findNavController().navigate(action)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(INSTANCE_STATE_PARAM_FEED, viewModel.feed)
        super.onSaveInstanceState(outState)
    }

    private fun setBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun init(savedInstanceState: Bundle?) {
        viewModel.feed = if (savedInstanceState == null) {
            args.feedArg
        } else {
            savedInstanceState.getSerializable(INSTANCE_STATE_PARAM_FEED) as FeedEntity
        }
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }
}
