package com.danil.kleshchin.rss.screens.sections.entities

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.domain.entity.Section

/**
 * Entity of [Section] modified for UI needs: added icon and display name
 */
enum class SectionEntity(
    val id: Int,
    val displayName: String,
    @DrawableRes val iconId: Int
) {
    Arts(0, "Arts", R.drawable.ic_section_arts),
    Automobiles(1, "Automobiles", R.drawable.ic_section_automobiles),
    Books(2, "Books", R.drawable.ic_section_books),
    Business(3, "Business", R.drawable.ic_section_business),
    Fashion(4, "Fashion", R.drawable.ic_section_fashion),
    Food(5, "Food", R.drawable.ic_section_food),
    Health(6, "Health", R.drawable.ic_section_health),
    Home(7, "Home", R.drawable.ic_section_home),
    Insider(8, "Insider", R.drawable.ic_section_insider),
    Magazine(9, "Magazine", R.drawable.ic_section_magazine),
    Movies(10, "Movies", R.drawable.ic_section_movies),
    Nyregion(11, "NY region", R.drawable.ic_section_nyregion),
    Obituaries(12, "Obituaries", R.drawable.ic_section_obituares),
    Opinion(13, "Opinion", R.drawable.ic_section_opinion),
    Politics(14, "Politics", R.drawable.ic_section_politics),
    Realestate(15, "Real estate", R.drawable.ic_section_realestate),
    Science(16, "Science", R.drawable.ic_section_science),
    Sports(17, "Sports", R.drawable.ic_section_sports),
    Sundayreview(18, "Sunday review", R.drawable.ic_section_sundayreview),
    Technology(19, "Technology", R.drawable.ic_section_technology),
    Theater(20, "Theater", R.drawable.ic_section_theater),
    `T-Magazine`(21, "T-Magazine", R.drawable.ic_section_magazine),
    Travel(22, "Travel", R.drawable.ic_section_travel),
    Upshot(23, "Upshot", R.drawable.ic_section_obituares),
    US(24, "US", R.drawable.ic_section_us),
    World(25, "World", R.drawable.ic_section_world);

    fun toSection(): Section {
        for (section in Section.values()) {
            if (this.id == section.id) {
                return section
            }
        }
        throw IllegalArgumentException("Unknown section: ${this.name}")
    }
}
