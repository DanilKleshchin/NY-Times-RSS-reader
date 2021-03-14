package com.danil.kleshchin.rss.utils

import android.content.Context
import android.content.res.Resources
import com.danil.kleshchin.rss.R
import javax.inject.Inject

class ResourceHelper @Inject constructor(
    private val context: Context
) {

    fun getDateCreated(dateTime: String) = context.getString(R.string.date_created, dateTime)

    fun getDateUpdated(dateTime: String) = context.getString(R.string.date_updated, dateTime)

    fun getAndroidResources(): Resources = context.resources
}
