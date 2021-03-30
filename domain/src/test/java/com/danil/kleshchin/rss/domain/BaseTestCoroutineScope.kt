package com.danil.kleshchin.rss.domain

import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope

abstract class BaseTestCoroutineScope {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    protected val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
}
