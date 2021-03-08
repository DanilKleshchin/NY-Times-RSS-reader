package com.danil.kleshchin.rss.screens.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.danil.kleshchin.rss.NYTimesRSSFeedsApp
import com.danil.kleshchin.rss.databinding.FragmentSectionsBinding
import com.danil.kleshchin.rss.entities.section.SectionEntity
import com.danil.kleshchin.rss.screens.sections.adapters.SectionListAdapter

class SectionFragment : Fragment(), SectionListAdapter.OnSectionClickListener {

    private val viewModel: SectionViewModel by viewModels()

    private var _binding: FragmentSectionsBinding? = null
    private val binding get() = _binding!!

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
        subscribeUi()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSectionClick(section: SectionEntity) {
        navigateToFeedScreen(section)
    }

    private fun subscribeUi() {
        viewModel.sections.observe(viewLifecycleOwner) { sections ->
            binding.sectionListView.adapter = SectionListAdapter(sections, requireContext(), this)
        }
    }

    private fun navigateToFeedScreen(section: SectionEntity) {
        val action = SectionFragmentDirections.actionSectionFragmentToFeedsListFragment(section)
        findNavController().navigate(action)
    }

    private fun initViewModel() {
        NYTimesRSSFeedsApp.INSTANCE.sectionComponent.inject(viewModel)
    }
}
