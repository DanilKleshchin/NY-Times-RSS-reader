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
import com.danil.kleshchin.rss.screens.feedslist.FeedsListFragment
import com.danil.kleshchin.rss.screens.sections.adapters.SectionListAdapter
import com.danil.kleshchin.rss.entities.section.SectionEntity
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
        sectionPresenter.onDetach()
    }

    override fun showSectionList(sectionList: List<SectionEntity>) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        binding.sectionListView.adapter = SectionListAdapter(sectionList, context, this)
    }

    override fun showLoadingView() {

    }

    override fun hideLoadingView() {

    }

    override fun showErrorMessage() {

    }

    override fun showFeedView(section: SectionEntity) {
        val context = activity ?: throw  IllegalStateException(ERROR_LOG_MESSAGE)
        initFeedsListView(context, section)
    }

    override fun onSectionClick(section: SectionEntity) {
        sectionPresenter.onSectionSelected(section)
    }

    //TODO where should I init feed component?
    private fun initFeedsListView(context: FragmentActivity, section: SectionEntity) {
        val feedsListFragment = FeedsListFragment.newInstance(section)
        (context.application as NYTimesRSSFeedsApp).initFeedsListComponent(feedsListFragment)
        (context.application as NYTimesRSSFeedsApp).getFeedsListComponent().inject(feedsListFragment)
        context.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, feedsListFragment)
            .commitNow()
    }
}
