package com.danil.kleshchin.rss.domain.interactor

import io.reactivex.Observable

interface UseCase<T> {

    fun execute(): Observable<T>
}
