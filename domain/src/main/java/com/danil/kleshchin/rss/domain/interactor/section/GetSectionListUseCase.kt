package com.danil.kleshchin.rss.domain.interactor.section

import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.domain.interactor.UseCase
import javax.inject.Inject

class GetSectionListUseCase @Inject constructor(): UseCase<List<Section>, Unit> {

    override suspend fun execute(params: Unit): List<Section> {
        return getSectionList()
    }

    fun getSectionList(): List<Section> {
        return Section.values().toList()
    }
}
