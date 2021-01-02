package com.danil.kleshchin.rss.screens.sections

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.danil.kleshchin.rss.databinding.FragmentSectionsBinding
import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.screens.feeds.FeedFragment
import com.danil.kleshchin.rss.screens.sections.adapters.SectionListAdapter
import java.lang.IllegalStateException
import javax.inject.Inject

class SectionFragment : Fragment(), SectionContract.View, Navigator,
    SectionListAdapter.OnSectionClickListener {

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
        val context = activity ?: throw  IllegalStateException("Section fragment wasn't attached.")
        binding.sectionListView.adapter = SectionListAdapter(sectionList, context, this)
    }

    override fun showLoadingView() {

    }

    override fun hideLoadingView() {

    }

    override fun showErrorMessage() {

    }

    //TODO think about this moment
    override fun showFeedView(section: Section) {
        activity?.let {
            it.supportFragmentManager
                .beginTransaction()
                .add(FeedFragment(), "TAG")
                .commitNow()
            return
        }
        showErrorMessage()
        Log.e("TAG", "Can't start feeds fragment for section: ${section.name}")
    }

    override fun onSectionClick(section: Section) {
        sectionPresenter.onSectionSelected(section)
    }
}
