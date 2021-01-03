package com.danil.kleshchin.rss.screens.feeds

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.entity.Section
import javax.inject.Inject

class FeedFragment : Fragment(), FeedContract.View, FeedNavigator {

    @Inject
    lateinit var feedPresenter: FeedContract.Presenter
    companion object {
        fun newInstance(section: Section): FeedFragment {
            //todo save section and show feed by section on create
            return FeedFragment()
        }
    }

    override fun showLoadingView() {

    }

    override fun hideLoadingView() {

    }

    override fun showErrorMessage() {

    }

    override fun showFeedList(feedList: List<Feed>) {

    }
}
