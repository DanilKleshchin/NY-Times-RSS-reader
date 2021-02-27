package com.danil.kleshchin.rss.data.feeds.datasource.network

const val baseUrl = "https://api.nytimes.com/svc/topstories/v2/"
const val API_TIMEOUT_SECONDS = 5L

//The API sends images in several formats. Use the next formats for Retrieving thumb and full size images
const val THUMB_FORMAT = "Normal"
const val ICON_FORMAT = "superJumbo"

const val UNKNOWN_DATA = "" //Pass empty string and then check for empty
