package com.danil.kleshchin.rss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.danil.kleshchin.rss.databinding.FragmentHomeViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeViewPagerFragment : Fragment() {

    private var _binding: FragmentHomeViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = HomeViewPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            SECTIONS_LIST_PAGE_INDEX -> R.drawable.section_tab_selector
            FAVORITE_FEEDS_LIST_PAGE_INDEX -> R.drawable.favorites_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            SECTIONS_LIST_PAGE_INDEX -> getString(R.string.sections_title)
            FAVORITE_FEEDS_LIST_PAGE_INDEX -> getString(R.string.favorites_title)
            else -> null
        }
    }
}
