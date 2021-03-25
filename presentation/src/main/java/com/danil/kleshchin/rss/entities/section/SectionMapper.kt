package com.danil.kleshchin.rss.entities.section

import com.danil.kleshchin.rss.domain.entity.Section
import javax.inject.Inject


class SectionMapper @Inject constructor() {

    fun transform(section: Section): SectionEntity {
        for (sectionEntity in SectionEntity.values()) {
            if (sectionEntity.id == section.id) {
                return sectionEntity
            }
        }
        throw IllegalArgumentException("Unknown section: ${section.name}")
    }

    fun transform(sectionEntity: SectionEntity): Section {
        for (section in Section.values()) {
            if (sectionEntity.id == section.id) {
                return section
            }
        }
        throw IllegalArgumentException("Unknown section: ${sectionEntity.name}")
    }
}
