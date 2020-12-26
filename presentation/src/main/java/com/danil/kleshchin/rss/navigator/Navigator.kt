package com.danil.kleshchin.rss.navigator

import com.danil.kleshchin.rss.domain.entity.Section

interface Navigator {

    fun showWebPage(url: String)

    fun showFeedView(section: Section)
}
