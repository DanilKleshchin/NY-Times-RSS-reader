package com.danil.kleshchin.rss.screens.sections

import android.content.Context
import androidx.fragment.app.Fragment
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.domain.entity.Section
import javax.inject.Inject

class SectionFragment: Fragment(R.layout.fragment_sections), SectionContract.View {

    @Inject
    private lateinit var sectionPresenter: SectionContract.Presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)

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


}
