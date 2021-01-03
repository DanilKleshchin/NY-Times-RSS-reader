package com.danil.kleshchin.rss.screens.feeds

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.danil.kleshchin.rss.databinding.FragmentFeedsBinding
import com.danil.kleshchin.rss.domain.entity.Feed
import java.lang.NullPointerException
import javax.inject.Inject

class FeedFragment : Fragment(), FeedContract.View, FeedNavigator {

    @Inject
    lateinit var feedPresenter: FeedContract.Presenter

    private var sectionName: String? = null

    private var _binding: FragmentFeedsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val KEY_SECTION_NAME = "KEY_SECTION_NAME"
        private val INSTANCE_STATE_PARAM_SECTION_NAME = "STATE_PARAM_SECTION_NAME"

        fun newInstance(sectionName: String): FeedFragment {
            val feedFragment = FeedFragment()
            val args = Bundle()
            args.putString(KEY_SECTION_NAME, sectionName)
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
        loadFeedList()

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

    }

    override fun showWebPage(url: String) {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(INSTANCE_STATE_PARAM_SECTION_NAME, sectionName)
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
        sectionName = if (savedInstanceState == null) {
            getSectionName()
        } else {
            savedInstanceState.getString(INSTANCE_STATE_PARAM_SECTION_NAME)
        }
        Log.d("TAG", "initializeFragment: $sectionName")
    }

    private fun loadFeedList() {
        val sectionName = getSectionName()
        feedPresenter.initialize(sectionName ?: throw NullPointerException("Section is null"))
    }

    private fun getSectionName(): String? {
        return arguments?.getString(KEY_SECTION_NAME)
    }

    private fun finish() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commitNow()
    }
}
