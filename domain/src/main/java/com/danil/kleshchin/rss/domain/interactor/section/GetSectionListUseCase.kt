package com.danil.kleshchin.rss.domain.interactor.section

import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.domain.interactor.UseCase
import io.reactivex.Observable

class GetSectionListUseCase: UseCase<List<Section>, Any> {

    override fun execute(params: Any): Observable<List<Section>> {
        return Observable.just(getSectionList())
    }

    fun getSectionList(): List<Section> {
        return Section.values().toList()
    }
}
