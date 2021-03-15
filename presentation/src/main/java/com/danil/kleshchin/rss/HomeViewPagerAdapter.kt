package com.danil.kleshchin.rss

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.danil.kleshchin.rss.screens.favorites.FavoriteFeedsListFragment
import com.danil.kleshchin.rss.screens.sections.SectionFragment

const val SECTIONS_LIST_PAGE_INDEX = 0
const val FAVORITE_FEEDS_LIST_PAGE_INDEX = 1

class HomeViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        SECTIONS_LIST_PAGE_INDEX to { SectionFragment() },
        FAVORITE_FEEDS_LIST_PAGE_INDEX to { FavoriteFeedsListFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
