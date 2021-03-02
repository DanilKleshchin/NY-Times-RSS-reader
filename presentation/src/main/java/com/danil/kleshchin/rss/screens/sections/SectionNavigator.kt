package com.danil.kleshchin.rss.screens.sections

import com.danil.kleshchin.rss.screens.sections.entities.SectionEntity

interface SectionNavigator {

    fun showFeedView(section: SectionEntity)
}
