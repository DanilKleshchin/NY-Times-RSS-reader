package com.danil.kleshchin.rss.domain.interactor

interface UseCase<T, Params> {

    suspend fun execute(params: Params): T
}
