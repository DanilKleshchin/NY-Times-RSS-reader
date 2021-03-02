package com.danil.kleshchin.rss.screens.sections.entities

import com.danil.kleshchin.rss.domain.entity.Section
import javax.inject.Inject

class SectionMapper @Inject constructor() {

    fun transform(sectionList: List<Section>): List<SectionEntity> {
        val sectionEntityList = arrayListOf<SectionEntity>()
        for (section in sectionList) {
            sectionEntityList.add(transform(section))
        }
        return sectionEntityList
    }

    private fun transform(section: Section): SectionEntity {
        for (sectionEntity in SectionEntity.values()) {
            if (sectionEntity.id == section.id) {
                return sectionEntity
            }
        }
        throw IllegalArgumentException("Unknown section: ${section.name}")
    }
}
