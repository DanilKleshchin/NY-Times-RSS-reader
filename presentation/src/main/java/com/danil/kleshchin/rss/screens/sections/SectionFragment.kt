package com.danil.kleshchin.rss.screens.sections

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.screens.feeds.FeedFragment
import javax.inject.Inject

class SectionFragment: Fragment(R.layout.fragment_sections), SectionContract.View, Navigator {

    @Inject
    lateinit var sectionPresenter: SectionContract.Presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        sectionPresenter.setView(this)
        sectionPresenter.onAttach()
    }

    override fun showSectionList(sectionList: List<Section>) {

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
}
