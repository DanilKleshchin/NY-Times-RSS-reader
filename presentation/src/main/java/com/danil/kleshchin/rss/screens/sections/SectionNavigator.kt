package com.danil.kleshchin.rss.screens.sections

import com.danil.kleshchin.rss.entities.section.SectionEntity

interface SectionNavigator {

    fun showFeedView(section: SectionEntity)
}
