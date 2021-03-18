package com.danil.kleshchin.rss.domain.interactor.features.feedslist

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Error(val exception: Exception) : ResultWrapper<Nothing>()
}
