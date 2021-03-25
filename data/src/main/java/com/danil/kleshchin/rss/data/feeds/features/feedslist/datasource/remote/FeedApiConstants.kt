package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote

const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
const val API_TIMEOUT_SECONDS = 5L

//The API params
const val API_KEY_PARAM = "api-key"
const val SECTION_NAME_PARAM = "section_name"
const val GET_SECTION_PARAM = "{$SECTION_NAME_PARAM}.json"

//The API sends images in several formats. Use the next formats for retrieving thumb and full size images
const val THUMB_FORMAT = "Normal"
const val ICON_FORMAT = "superJumbo"

const val UNKNOWN_DATA = "" //Pass empty string and then check for empty
