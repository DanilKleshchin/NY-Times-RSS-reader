package com.danil.kleshchin.rss.domain.interactor.section

import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.domain.interactor.UseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetSectionListUseCase @Inject constructor(): UseCase<List<Section>, Unit> {

    override fun execute(params: Unit): Observable<List<Section>> {
        return Observable.just(getSectionList())
    }

    fun getSectionList(): List<Section> {
        return Section.values().toList()
    }
}
