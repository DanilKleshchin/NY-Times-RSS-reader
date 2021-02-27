package com.danil.kleshchin.rss.domain.entity

/**
 * Every TopStories feed has been divided into the next sections.
 */
//TODO move this to DB
enum class Section {
    ARTS,
    AUTOMOBILES,
    BOOKS,
    BUSINESS,
    FASHION,
    FOOD,
    HEALTH,
    HOME,
    INSIDER,
    MAGAZINE,
    MOVIES,
    NYREGION("NY REGION"),
    OBITUARIES,
    OPINION,
    POLITICS,
    REALESTATE("REAL ESTATE"),
    SCIENCE,
    SPORTS,
    SUNDAYREVIEW("SUNDAY REVIEW"),
    TECHNOLOGY,
    THEATER,
    `T-MAGAZINE`,
    TRAVEL,
    UPSHOT,
    US,
    WORLD;

    val displayName: String

    constructor(displayName: String) {
        this.displayName = displayName
    }

    constructor() {
        this.displayName = this.name
    }
}
