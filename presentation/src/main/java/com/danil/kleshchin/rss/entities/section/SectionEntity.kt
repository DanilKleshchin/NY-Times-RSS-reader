package com.danil.kleshchin.rss.entities.section

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.domain.entity.Section
import java.io.Serializable

/**
 * Entity of [Section] modified for UI needs: added icon and display name
 */
enum class SectionEntity(
    val id: Int,
    @StringRes val displayName: Int,
    @DrawableRes val iconId: Int
) : Serializable {
    Arts(0, R.string.section_arts, R.drawable.ic_section_arts),
    Automobiles(1, R.string.section_automobiles, R.drawable.ic_section_automobiles),
    Books(2, R.string.section_books, R.drawable.ic_section_books),
    Business(3, R.string.section_business, R.drawable.ic_section_business),
    Fashion(4, R.string.section_fashion, R.drawable.ic_section_fashion),
    Food(5, R.string.section_food, R.drawable.ic_section_food),
    Health(6, R.string.section_health, R.drawable.ic_section_health),
    Home(7, R.string.section_home, R.drawable.ic_section_home),
    Insider(8, R.string.section_insider, R.drawable.ic_section_insider),
    Magazine(9, R.string.section_magazine, R.drawable.ic_section_magazine),
    Movies(10, R.string.section_movies, R.drawable.ic_section_movies),
    Nyregion(11, R.string.section_ny_region, R.drawable.ic_section_nyregion),
    Obituaries(12, R.string.section_obituaries, R.drawable.ic_section_obituares),
    Opinion(13, R.string.section_opinion, R.drawable.ic_section_opinion),
    Politics(14, R.string.section_politics, R.drawable.ic_section_politics),
    Realestate(15, R.string.section_real_estate, R.drawable.ic_section_realestate),
    Science(16, R.string.section_science, R.drawable.ic_section_science),
    Sports(17, R.string.section_sports, R.drawable.ic_section_sports),
    Sundayreview(18, R.string.section_sunday_review, R.drawable.ic_section_sundayreview),
    Technology(19, R.string.section_technology, R.drawable.ic_section_technology),
    Theater(20, R.string.section_theater, R.drawable.ic_section_theater),
    `T-Magazine`(21, R.string.section_t_magazine, R.drawable.ic_section_magazine),
    Travel(22, R.string.section_travel, R.drawable.ic_section_travel),
    Upshot(23, R.string.section_upshot, R.drawable.ic_section_obituares),
    US(24, R.string.section_us, R.drawable.ic_section_us),
    World(25, R.string.section_world, R.drawable.ic_section_world);
}
