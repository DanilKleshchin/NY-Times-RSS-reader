package com.danil.kleshchin.rss.screens.feedslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danil.kleshchin.rss.NYTimesRSSFeedsApp
import com.danil.kleshchin.rss.databinding.FragmentFeedsListBinding
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.feed.FeedMapper
import com.danil.kleshchin.rss.entities.section.SectionEntity
import com.danil.kleshchin.rss.screens.feedslist.adapters.FeedsListAdapter
import com.danil.kleshchin.rss.widgets.VerticalSpaceItemDecoration
import javax.inject.Inject

class FeedsListFragment : Fragment(), FeedsListContract.View, FeedsListNavigator,
    FeedsListAdapter.OnFeedClickListener {

    private val INSTANCE_STATE_PARAM_SECTION = "STATE_PARAM_SECTION"
    private val LIST_ITEMS_MARGIN = 40

    @Inject
    lateinit var feedsListPresenter: FeedsListContract.Presenter

    private var section: SectionEntity? = null

    private var _binding: FragmentFeedsListBinding? = null
    private val binding get() = _binding!!
    private val args: FeedsListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NYTimesRSSFeedsApp.INSTANCE.initFeedsListComponent(this)
        NYTimesRSSFeedsApp.INSTANCE.getFeedsListComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeFragment(savedInstanceState)

        _binding = FragmentFeedsListBinding.inflate(inflater, container, false)
        binding.feedListView.addItemDecoration(VerticalSpaceItemDecoration(LIST_ITEMS_MARGIN))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedsListPresenter.setView(this)
        feedsListPresenter.onAttach()
        initPresenterForSection()
        setBackPressedCallback()

        binding.backButton.setOnClickListener { finish() }
        binding.refreshView.setOnRefreshListener { feedsListPresenter.onRefreshSelected() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        feedsListPresenter.onDetach()
    }

    override fun showLoadingView() {
        binding.refreshView.isRefreshing = true
    }

    override fun hideLoadingView() {
        binding.refreshView.isRefreshing = false
    }

    override fun showRetry() {

    }

    override fun hideRetry() {

    }

    override fun showErrorMessage() {

    }

    override fun showSectionName(sectionName: String) {
        binding.sectionName.text = sectionName
    }

    override fun showFeedList(feedList: List<Feed>, mapper: FeedMapper) {
        val currentTime = System.currentTimeMillis()
        val feedEntityList = mapper.transform(feedList, currentTime, resources)
        binding.feedListView.adapter = FeedsListAdapter(feedEntityList, requireContext(), this)
    }

    override fun onFeedClick(feed: FeedEntity) {
        feedsListPresenter.onFeedSelected(feed)
    }

    override fun navigateToFeedView(feed: FeedEntity) {
        val action = FeedsListFragmentDirections.actionFeedsListFragmentToFeedFragment(feed)
        findNavController().navigate(action)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(INSTANCE_STATE_PARAM_SECTION, section)
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

    private fun initializeFragment(savedInstanceState: Bundle?) {
        section = if (savedInstanceState == null) {
            getSection()
        } else ({
            savedInstanceState.getSerializable(INSTANCE_STATE_PARAM_SECTION)
        }) as SectionEntity
    }

    private fun initPresenterForSection() {
        val section = getSection()
        feedsListPresenter.initialize(section)
    }

    private fun getSection(): SectionEntity {
        return args.sectionArg
    }

    private fun finish() {
        findNavController().popBackStack()
    }
}
