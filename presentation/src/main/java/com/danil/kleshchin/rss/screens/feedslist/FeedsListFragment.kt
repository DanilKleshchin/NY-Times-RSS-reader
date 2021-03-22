package com.danil.kleshchin.rss.screens.feedslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danil.kleshchin.rss.NYTimesRSSFeedsApp
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.FragmentFeedsListBinding
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.ResultWrapper
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.section.SectionEntity
import com.danil.kleshchin.rss.screens.feedslist.adapters.FeedsListAdapter
import com.danil.kleshchin.rss.widgets.VerticalSpaceItemDecoration
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FeedsListFragment : Fragment(), FeedsListAdapter.OnFeedClickListener {

    private val INSTANCE_STATE_PARAM_SECTION = "STATE_PARAM_SECTION"
    private val LIST_ITEMS_MARGIN = 40

    private val viewModel: FeedsListViewModel by viewModels()
    private val args: FeedsListFragmentArgs by navArgs()
    private var loadFeedsListJob: Job? = null

    private var _binding: FragmentFeedsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedsListBinding.inflate(inflater, container, false)

        init(savedInstanceState)
        changeLoadingViewVisibility(true)
        loadFeedsList()

        binding.feedListView.addItemDecoration(VerticalSpaceItemDecoration(LIST_ITEMS_MARGIN))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackPressedCallback()

        binding.apply {
            section = viewModel.section
            setBackClickListener { navigateBack() }
            setRetryClickListener { loadFeedsList() }
            refreshView.setOnRefreshListener { loadFeedsList() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFeedClick(feed: FeedEntity) {
        navigateToFeedScreen(feed)
    }

    override fun onStarClick(feed: FeedEntity) {
        viewModel.addRemoveFavoriteFeed(feed)
    }

    override fun onShareClick(feed: FeedEntity) {
        createShareIntent(feed)
    }

    private fun changeErrorViewVisibility(isVisible: Boolean) {
        binding.apply {
            errorContainer.isVisible = isVisible
            refreshView.isVisible = isVisible.not()
        }
    }

    private fun changeLoadingViewVisibility(isVisible: Boolean) {
        binding.refreshView.isRefreshing = isVisible
    }

    private fun loadFeedsList() {
        loadFeedsListJob?.cancel()
        loadFeedsListJob = lifecycleScope.launch {
            viewModel.loadFeedsList().observe(viewLifecycleOwner) { feeds ->
                when(feeds) {
                    is ResultWrapper.Success -> setFavoritesToFeedList(feeds.value)
                    is ResultWrapper.Error -> onErrorReceived(feeds.exception)
                    is ResultWrapper.NetworkError -> showNetworkErrorView()
                }
            }
        }
    }

    private fun setFavoritesToFeedList(feedList: List<Feed>) {
        loadFeedsListJob?.cancel()
        loadFeedsListJob = lifecycleScope.launch {
            viewModel.getFeedListWithFavorites(feedList).observe(viewLifecycleOwner) { feeds ->
                changeLoadingViewVisibility(false)
                changeErrorViewVisibility(false)
                showFeedList(feeds)
            }
        }
    }

    private fun showFeedList(feedList: List<FeedEntity>) {
        binding.feedListView.adapter = FeedsListAdapter(feedList, requireContext(), this)
    }

    private fun onErrorReceived(exception: Exception) {
        changeLoadingViewVisibility(false)
        changeErrorViewVisibility(true)
        exception.printStackTrace()
    }

    private fun showNetworkErrorView() {
        changeLoadingViewVisibility(false)
        changeErrorViewVisibility(true)
        Snackbar.make(binding.root, getString(R.string.network_error_message), LENGTH_LONG).show()
    }

    private fun navigateToFeedScreen(feed: FeedEntity) {
        val action = FeedsListFragmentDirections.actionFeedsListFragmentToFeedFragment(feed)
        findNavController().navigate(action)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(INSTANCE_STATE_PARAM_SECTION, viewModel.section)
        super.onSaveInstanceState(outState)
    }

    private fun init(savedInstanceState: Bundle?) {
        viewModel.section = if (savedInstanceState == null) {
            args.sectionArg
        } else (savedInstanceState.getSerializable(INSTANCE_STATE_PARAM_SECTION)) as SectionEntity
    }

    private fun createShareIntent(feed: FeedEntity) {
        ShareCompat.IntentBuilder.from(requireActivity())
            .setType("text/plain")
            .setChooserTitle(getString(R.string.read_full_article))
            .setText(feed.pageUrl)
            .startChooser()
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

    private fun initViewModel() {
        NYTimesRSSFeedsApp.INSTANCE.feedsListComponent.inject(viewModel)
    }
}
