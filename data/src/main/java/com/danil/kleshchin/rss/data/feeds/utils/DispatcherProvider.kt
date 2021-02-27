package com.danil.kleshchin.rss.data.feeds.utils

import kotlinx.coroutines.CoroutineDispatcher

data class DispatcherProvider(
    val database: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val network: CoroutineDispatcher
)
