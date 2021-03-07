package com.danil.kleshchin.rss.screens.sections

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.danil.kleshchin.rss.NYTimesRSSFeedsApp
import com.danil.kleshchin.rss.databinding.FragmentSectionsBinding
import com.danil.kleshchin.rss.entities.section.SectionEntity
import com.danil.kleshchin.rss.screens.sections.adapters.SectionListAdapter
import javax.inject.Inject

class SectionFragment : Fragment(), SectionContract.View, SectionNavigator,
    SectionListAdapter.OnSectionClickListener {

    @Inject
    lateinit var sectionPresenter: SectionContract.Presenter

    private var _binding: FragmentSectionsBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        NYTimesRSSFeedsApp.INSTANCE.initSectionComponent(this)
        NYTimesRSSFeedsApp.INSTANCE.getSectionComponent().inject(this)
        sectionPresenter.onAttach()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectionsBinding.inflate(inflater, container, false)
        sectionPresenter.setView(this)
        sectionPresenter.initialize()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        sectionPresenter.onDetach()
    }

    override fun showSectionList(sectionList: List<SectionEntity>) {
        binding.sectionListView.adapter = SectionListAdapter(sectionList, requireContext(), this)
    }

    override fun showLoadingView() {

    }

    override fun hideLoadingView() {

    }

    override fun showErrorMessage() {

    }

    override fun showFeedView(section: SectionEntity) {
        val action = SectionFragmentDirections.actionSectionFragmentToFeedsListFragment(section)
        findNavController().navigate(action)
    }

    override fun onSectionClick(section: SectionEntity) {
        sectionPresenter.onSectionSelected(section)
    }
}
