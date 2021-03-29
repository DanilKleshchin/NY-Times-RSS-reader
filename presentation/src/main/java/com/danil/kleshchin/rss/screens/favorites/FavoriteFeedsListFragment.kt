package com.danil.kleshchin.rss.screens.favorites

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
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.rss.NYTimesRSSFeedsApp
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.FragmentFavoriteFeedsListBinding
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.screens.HomeViewPagerFragmentDirections
import com.danil.kleshchin.rss.screens.favorites.adapters.FavoriteFeedsListAdapter
import com.danil.kleshchin.rss.widgets.VerticalSpaceItemDecoration
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoriteFeedsListFragment : Fragment(), FavoriteFeedsListAdapter.OnFeedClickListener {

    private val LIST_ITEMS_MARGIN = 40

    private val viewModel: FavoriteFeedsListViewModel by viewModels()
    private var loadFeedsListJob: Job? = null

    private var _binding: FragmentFavoriteFeedsListBinding? = null
    private val binding get() = _binding!!

    private var undoSnackbar:Snackbar? = null

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
        undoSnackbar = null
    }

    override fun onFeedClick(feed: FeedEntity) {
        navigateToFeedScreen(feed)
    }

    override fun onStarClick(viewHolder: RecyclerView.ViewHolder, feed: FeedEntity) {
        removeFeed(viewHolder.adapterPosition)
    }

    override fun onShareClick(feed: FeedEntity) {
        createShareIntent(feed)
    }

    private fun changeEmptyViewVisibility(isVisible: Boolean) {
        binding.apply {
            emptyViewContainer.isVisible = isVisible
            favoriteFeedsListView.isVisible = isVisible.not()
        }
    }

    private fun loadFavoriteFeedsList() {
        loadFeedsListJob?.cancel()
        loadFeedsListJob = lifecycleScope.launch {
            viewModel.loadFavoriteFeedsList().observe(viewLifecycleOwner) { feeds ->
                if (feeds.isEmpty()) {
                    changeEmptyViewVisibility(true)
                } else {
                    changeEmptyViewVisibility(false)
                    showFeedList(feeds)
                }
            }
        }
    }

    private fun showFeedList(feedList: List<FeedEntity>) {
        binding.favoriteFeedsListView.adapter =
            FavoriteFeedsListAdapter(feedList, requireContext(), this)
    }

    private fun removeFeed(position: Int) {
        viewModel.removeFeed(position)
        updateAdapterFeedsList(viewModel.feedList)
        binding.favoriteFeedsListView.adapter?.notifyItemRemoved(position)
        showUndoSnackbar()
    }

    private fun showUndoSnackbar() {
        undoSnackbar?.dismiss()
        undoSnackbar = Snackbar.make(
            binding.root, getString(R.string.undo_snack_bar_title),
            Snackbar.LENGTH_LONG
        )
        undoSnackbar?.setAction(getString(R.string.undo_snackbar_undo_text)) {
            undoRemoving()
        }
        undoSnackbar?.show()
    }

    private fun undoRemoving() {
        viewModel.undoFeedRemoving()
        updateAdapterFeedsList(viewModel.feedList)
        binding.favoriteFeedsListView.adapter?.notifyItemInserted(viewModel.feedToRemovePosition)
        binding.favoriteFeedsListView.scrollToPosition(viewModel.feedToRemovePosition)
    }

    private fun updateAdapterFeedsList(feedList: List<FeedEntity>) {
        (binding.favoriteFeedsListView.adapter as FavoriteFeedsListAdapter).updateFeedList(feedList)
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
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initViewModel() {
        NYTimesRSSFeedsApp.INSTANCE.favoriteFeedsListComponent.inject(viewModel)
    }
}
