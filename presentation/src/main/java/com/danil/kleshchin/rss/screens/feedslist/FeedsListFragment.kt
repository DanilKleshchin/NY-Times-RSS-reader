package com.danil.kleshchin.rss.screens.feedslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.danil.kleshchin.rss.NYTimesRSSFeedsApp
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.FragmentFeedsListBinding
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.screens.feed.FeedFragment
import com.danil.kleshchin.rss.screens.feedslist.adapters.FeedsListAdapter
import com.danil.kleshchin.rss.screens.sections.entities.SectionEntity
import javax.inject.Inject

class FeedsListFragment : Fragment(), FeedsListContract.View, FeedsListNavigator,
    FeedsListAdapter.OnFeedClickListener {

    private val ERROR_LOG_MESSAGE = "Section fragment wasn't attached."

    @Inject
    lateinit var feedsListPresenter: FeedsListContract.Presenter

    private var section: SectionEntity? = null

    private var _binding: FragmentFeedsListBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val KEY_SECTION = "KEY_SECTION"
        private val INSTANCE_STATE_PARAM_SECTION = "STATE_PARAM_SECTION"

        fun newInstance(section: SectionEntity): FeedsListFragment {
            val feedFragment = FeedsListFragment()
            val args = Bundle()
            args.putSerializable(KEY_SECTION, section)
            feedFragment.arguments = args
            return feedFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeFragment(savedInstanceState)

        _binding = FragmentFeedsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedsListPresenter.setView(this)
        feedsListPresenter.onAttach()
        initPresenterForSection()

        binding.backButton.setOnClickListener { finish() }
        binding.refreshView.setOnRefreshListener { feedsListPresenter.onRefreshSelected() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setBackPressedCallback()
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

    override fun showFeedList(feedList: List<Feed>) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        binding.feedListView.adapter = FeedsListAdapter(feedList, context, this)
    }

    override fun onFeedClick(feed: Feed) {
        feedsListPresenter.onFeedSelected(feed)
    }

    override fun navigateToFeedView(feed: Feed) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        initFeedView(context, feed)
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
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initializeFragment(savedInstanceState: Bundle?) {
        section = if (savedInstanceState == null) {
            getSection()
        } else ({
            savedInstanceState.getSerializable(INSTANCE_STATE_PARAM_SECTION)
        }) as SectionEntity?
    }

    private fun initPresenterForSection() {
        val section = getSection()
        feedsListPresenter.initialize(section ?: throw NullPointerException("Section is null"))
    }

    private fun getSection(): SectionEntity? {
        return arguments?.getSerializable(KEY_SECTION) as SectionEntity?
    }

    private fun initFeedView(context: FragmentActivity, feed: Feed) {
        val feedFragment = FeedFragment.newInstance(feed)
        (context.application as NYTimesRSSFeedsApp).initFeedComponent(feedFragment)
        (context.application as NYTimesRSSFeedsApp).getFeedComponent().inject(feedFragment)
        context.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, feedFragment)
            .commitNow()
    }

    private fun finish() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commitNow()
    }
}
