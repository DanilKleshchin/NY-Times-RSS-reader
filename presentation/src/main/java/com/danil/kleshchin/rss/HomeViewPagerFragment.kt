package com.danil.kleshchin.rss

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
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

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    var test = false

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.light_dark_mode_menu -> {
                item.setIcon(if (test) {
                    R.drawable.ic_light_mode
                } else {
                    R.drawable.ic_dark_mode
                })
                test = test.not()
                setTheme()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setTheme() {
        if (binding.lightDarkModeStub.isVisible) {
            return
        }

        val w = binding.coordinatorLayout.measuredWidth
        val h = binding.coordinatorLayout.measuredHeight

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        binding.coordinatorLayout.draw(canvas)

        binding.lightDarkModeStub.setImageBitmap(bitmap)
        binding.lightDarkModeStub.isVisible = true

        //val finalRadius = kotlin.math.hypot(w.toFloat(), h.toFloat())
        val finalRadius = kotlin.math.hypot(w.toFloat(), h.toFloat()) / 2

        val anim = ViewAnimationUtils.createCircularReveal(binding.coordinatorLayout, w / 2, h / 2, 0f, finalRadius)
        anim.duration = 400L
        anim.doOnEnd {
            binding.lightDarkModeStub.setImageDrawable(null)
            binding.lightDarkModeStub.isVisible = false
        }
        anim.start()
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
