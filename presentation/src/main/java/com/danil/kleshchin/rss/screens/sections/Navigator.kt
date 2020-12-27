package com.danil.kleshchin.rss.screens.sections

import com.danil.kleshchin.rss.domain.entity.Section

//TODO replace with Cicerone
interface Navigator {

    fun showFeedView(section: Section)
}
