package com.danil.kleshchin.rss.domain.interactor

import io.reactivex.Observable

interface UseCase<T, Params> {

    fun execute(params: Params): Observable<T>
}
