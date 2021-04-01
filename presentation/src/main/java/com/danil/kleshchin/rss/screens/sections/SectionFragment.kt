package com.danil.kleshchin.rss.screens.sections

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.danil.kleshchin.rss.NYTimesRSSFeedsApp
import com.danil.kleshchin.rss.databinding.FragmentSectionsBinding
import com.danil.kleshchin.rss.entities.section.SectionEntity
import com.danil.kleshchin.rss.screens.HomeViewPagerFragmentDirections
import com.danil.kleshchin.rss.screens.sections.adapters.SectionListAdapter

class SectionFragment : Fragment() {

    private val SPANS_COUNT_PORTRAIT = 3
    private val SPANS_COUNT_LANDSCAPE = 5

    private val viewModel: SectionViewModel by viewModels()

    private var _binding: FragmentSectionsBinding? = null
    private val binding get() = _binding!!

    private var sectionListAdapter: SectionListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectionsBinding.inflate(inflater, container, false)
        setUpSectionRecyclerView()
        loadSectionList()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        sectionListAdapter = null
    }

    private fun setUpSectionRecyclerView() {
        val orientation = this.resources.configuration.orientation
        val spansCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            SPANS_COUNT_PORTRAIT
        } else {
            SPANS_COUNT_LANDSCAPE
        }
        binding.sectionListView.layoutManager =
            GridLayoutManager(requireContext(), spansCount, LinearLayoutManager.VERTICAL, false)
        sectionListAdapter = SectionListAdapter(requireContext(), this::onSectionClick)
        binding.sectionListView.adapter = sectionListAdapter
    }

    private fun loadSectionList() {
        viewModel.sections.observe(viewLifecycleOwner) { sections ->
            sectionListAdapter?.sectionList = sections
        }
    }

    private fun onSectionClick(section: SectionEntity) {
        navigateToFeedScreen(section)
    }

    private fun navigateToFeedScreen(section: SectionEntity) {
        val action =
            HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToFeedsListFragment(section)
        findNavController().navigate(action)
    }

    private fun initViewModel() {
        NYTimesRSSFeedsApp.INSTANCE.sectionComponent.inject(viewModel)
    }
}
