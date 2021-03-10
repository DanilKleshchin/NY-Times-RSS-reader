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
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.FragmentFeedBinding
import com.danil.kleshchin.rss.entities.feed.FeedEntity


class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()
    private val args: FeedFragmentArgs by navArgs()

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val INSTANCE_STATE_PARAM_FEED = "STATE_PARAM_FEED"
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
        showFeed(args.feedArg)

        binding.apply {
            feedViewModel = viewModel
            pageUrl.setOnClickListener { showWebPage() }
            iconShare.setOnClickListener { createShareIntent() }
            backButton.setOnClickListener { finish() }
            image.setOnClickListener { navigateToZoomImageScreen() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showFeed(feed: FeedEntity) {
        binding.apply {
            this.feed = feed
            dateCreated.text = getString(R.string.date_created, feed.dateCreated) //TODO check this - how to move to xml
            dateUpdated.text = getString(R.string.date_updated, feed.dateUpdated)
        }
    }

    private fun createShareIntent() {
        ShareCompat.IntentBuilder.from(requireActivity())
            .setType("text/plain")
            .setChooserTitle(getString(R.string.read_full_article))
            .setText(viewModel.feed.feedPageUrl)
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
                finish()
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

    private fun finish() {
        findNavController().popBackStack()
    }
}
