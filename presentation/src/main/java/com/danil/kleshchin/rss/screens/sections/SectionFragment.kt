package com.danil.kleshchin.rss.screens.sections

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.danil.kleshchin.rss.NYTimesRSSFeedsApp
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.FragmentSectionsBinding
import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.screens.feeds.FeedFragment
import com.danil.kleshchin.rss.screens.sections.adapters.SectionListAdapter
import javax.inject.Inject

class SectionFragment : Fragment(), SectionContract.View, SectionNavigator,
    SectionListAdapter.OnSectionClickListener {

    private val ERROR_LOG_MESSAGE = "Section fragment wasn't attached."

    @Inject
    lateinit var sectionPresenter: SectionContract.Presenter

    private var _binding: FragmentSectionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        sectionPresenter.setView(this)
        sectionPresenter.onAttach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showSectionList(sectionList: List<Section>) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        binding.sectionListView.adapter = SectionListAdapter(sectionList, context, this)
    }

    override fun showLoadingView() {

    }

    override fun hideLoadingView() {

    }

    override fun showErrorMessage() {

    }

    override fun showFeedView(section: Section) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        initFeedView(context, section)
    }

    override fun onSectionClick(section: Section) {
        sectionPresenter.onSectionSelected(section)
    }

    //TODO where should I init feed component?
    private fun initFeedView(context: FragmentActivity, section: Section) {
        val feedFragment = FeedFragment.newInstance(section.name)
        (context.application as NYTimesRSSFeedsApp).initFeedComponent(feedFragment)
        (context.application as NYTimesRSSFeedsApp).getFeedComponent().inject(feedFragment)
        context.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, feedFragment)
            .commitNow()
    }
}
