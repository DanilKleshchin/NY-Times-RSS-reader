package com.danil.kleshchin.rss

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danil.kleshchin.rss.screens.sections.SectionFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initSectionView()
    }

    //TODO think about location of dagger components initialization
    private fun initSectionView() {
        val sectionFragment = SectionFragment()
        (application as NYTimesRSSFeedsApp).initSectionComponent(sectionFragment)
        (application as NYTimesRSSFeedsApp).getSectionComponent().inject(sectionFragment)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, sectionFragment)
            .commitNow()
    }
}
