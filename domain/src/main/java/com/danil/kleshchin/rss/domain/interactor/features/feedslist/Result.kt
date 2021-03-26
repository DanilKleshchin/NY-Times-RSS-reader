package com.danil.kleshchin.rss.domain.interactor.features.feedslist

sealed class Result<out T> {
    data class Success<out T>(val value: T): Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object InvalidData : Result<Nothing>()
}
