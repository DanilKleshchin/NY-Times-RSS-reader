package com.danil.kleshchin.rss.screens.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.rss.NYTimesRSSFeedsApp
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.FragmentFavoriteFeedsListBinding
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.screens.HomeViewPagerFragmentDirections
import com.danil.kleshchin.rss.screens.favorites.adapters.FavoriteFeedsListAdapter
import com.danil.kleshchin.rss.widgets.VerticalSpaceItemDecoration
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoriteFeedsListFragment : Fragment(), FavoriteFeedsListAdapter.OnFeedClickListener {

    private val LIST_ITEMS_MARGIN = 40

    private val viewModel: FavoriteFeedsListViewModel by viewModels()
    private var loadFeedsListJob: Job? = null

    private var _binding: FragmentFavoriteFeedsListBinding? = null
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
        _binding = FragmentFavoriteFeedsListBinding.inflate(inflater, container, false)

        loadFavoriteFeedsList()

        binding.favoriteFeedsListView.addItemDecoration(
            VerticalSpaceItemDecoration(
                LIST_ITEMS_MARGIN
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackPressedCallback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFeedClick(feed: FeedEntity) {
        navigateToFeedScreen(feed)
    }

    override fun onStarClick(viewHolder: RecyclerView.ViewHolder, feed: FeedEntity) {
        (binding.favoriteFeedsListView.adapter as FavoriteFeedsListAdapter).removeFeed(
            viewHolder,
            binding.favoriteFeedsListView
        )
    }

    override fun onFeedUndoDismissed(feed: FeedEntity) {
        viewModel.addRemoveFavoriteFeed(feed)
    }

    override fun onShareClick(feed: FeedEntity) {
        createShareIntent(feed)
    }

    private fun showEmptyView() {
        //TODO do it XML
    }

    private fun loadFavoriteFeedsList() {
        loadFeedsListJob?.cancel()
        loadFeedsListJob = lifecycleScope.launch {
            viewModel.loadFavoriteFeedsList().observe(viewLifecycleOwner) { feeds ->
                showFeedList(feeds)
            }
        }
    }

    private fun showFeedList(feedList: List<FeedEntity>) {
        binding.favoriteFeedsListView.adapter =
            FavoriteFeedsListAdapter(ArrayList(feedList), requireContext(), this)
    }

    private fun navigateToFeedScreen(feed: FeedEntity) {
        val action =
            HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToFeedFragment(feed)
        findNavController().navigate(action)
    }

    //TODO move sharing to BaseFragment?
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
                requireActivity().finish() //TODO check
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initViewModel() {
        NYTimesRSSFeedsApp.INSTANCE.favoriteFeedsListComponent.inject(viewModel)
    }
}
