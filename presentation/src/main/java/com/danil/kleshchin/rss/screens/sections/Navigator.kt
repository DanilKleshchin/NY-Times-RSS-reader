package com.danil.kleshchin.rss.screens.sections

import com.danil.kleshchin.rss.domain.entity.Section

//TODO replace with Cicerone https://habr.com/ru/company/mobileup/blog/314838/
interface Navigator {

    fun showFeedView(section: Section)
}
