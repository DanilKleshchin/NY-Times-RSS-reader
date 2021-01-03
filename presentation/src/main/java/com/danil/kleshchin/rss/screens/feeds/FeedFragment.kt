package com.danil.kleshchin.rss.screens.feeds

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.danil.kleshchin.rss.databinding.FragmentFeedsBinding
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.screens.feeds.adapters.FeedListAdapter
import javax.inject.Inject

class FeedFragment : Fragment(), FeedContract.View, FeedNavigator,
    FeedListAdapter.OnFeedClickListener {

    private val ERROR_LOG_MESSAGE = "Section fragment wasn't attached."

    @Inject
    lateinit var feedPresenter: FeedContract.Presenter

    private var section: Section? = null

    private var _binding: FragmentFeedsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val KEY_SECTION = "KEY_SECTION"
        private val INSTANCE_STATE_PARAM_SECTION = "STATE_PARAM_SECTION"

        fun newInstance(section: Section): FeedFragment {
            val feedFragment = FeedFragment()
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

        _binding = FragmentFeedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedPresenter.setView(this)
        feedPresenter.onAttach()
        initPresenterForSection()

        binding.backButton.setOnClickListener { finish() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setBackPressedCallback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showLoadingView() {

    }

    override fun hideLoadingView() {

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
        binding.feedListView.adapter = FeedListAdapter(feedList, context, this)
    }

    override fun onFeedClick(feed: Feed) {
        feedPresenter.onFeedSelected(feed)
    }

    override fun showWebPage(url: String) {

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
        }) as Section?
    }

    private fun initPresenterForSection() {
        val section = getSection()
        feedPresenter.initialize(section ?: throw NullPointerException("Section is null"))
    }

    private fun getSection(): Section? {
        return arguments?.getSerializable(KEY_SECTION) as Section?
    }

    private fun finish() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commitNow()
    }
}
