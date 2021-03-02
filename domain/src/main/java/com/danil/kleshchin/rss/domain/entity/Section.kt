package com.danil.kleshchin.rss.domain.entity

/**
 * Every TopStories feed is divided into the next sections.
 */
enum class Section (val id: Int) {
    Arts(0),
    Automobiles(1),
    Books(2),
    Business(3),
    Fashion(4),
    Food(5),
    Health(6),
    Home(7),
    Insider(8),
    Magazine(9),
    Movies(10),
    Nyregion(11),
    Obituaries(12),
    Opinion(13),
    Politics(14),
    Realestate(15),
    Science(16),
    Sports(17),
    Sundayreview(18),
    Technology(19),
    Theater(20),
    `T-Magazine`(21),
    Travel(22),
    Upshot(23),
    US(24),
    World(25);
}
