package com.danil.kleshchin.rss.domain.entity

/**
 * Every RSS feed has been divided into the next sections.
 */
enum class Section {
    Africa,
    Americas,
    ArtandDesign("Art and Design"),
    Arts,
    AsiaPacific("Asia Pacific"),
    Automobile,
    Baseball,
    Books,
    Business,
    Climate,
    CollegeBasketball("College Basketball"),
    CollegeFootball("College Football"),
    Dance,
    Dealbook,
    DiningandWine("Dining and Wine"),
    Economy,
    Education,
    EnergyEnvironment("Energy Environment"),
    Europe,
    FashionandStyle("Fashion and Style"),
    Golf,
    Health,
    Hockey,
    HomePage("Home Page"),
    Jobs,
    Lens,
    MediaandAdvertising("Media and Advertising"),
    MiddleEast("Middle East"),
    MostEmailed("Most Emailed"),
    MostShared("Most Shared"),
    MostViewed("Most Viewed"),
    Movies,
    Music,
    NYRegion("NY Region"),
    Obituaries,
    PersonalTech("Personal Tech"),
    Politics,
    ProBasketball("Pro Basketball"),
    ProFootball("Pro Football"),
    RealEstate("Real Estate"),
    Science,
    SmallBusiness("Small Business"),
    Soccer,
    Space,
    Sports,
    SundayBookReview("Sunday Book Review"),
    `Sunday-Review`("Sunday Review"),
    Technology,
    Television,
    Tennis,
    Theater,
    TMagazine("T Magazine"),
    Travel,
    Upshot,
    US,
    Weddings,
    Well,
    YourMoney("Your Money");

    val displayName: String

    constructor(displayName: String) {
        this.displayName = displayName
    }

    constructor() {
        this.displayName = this.name
    }
}
