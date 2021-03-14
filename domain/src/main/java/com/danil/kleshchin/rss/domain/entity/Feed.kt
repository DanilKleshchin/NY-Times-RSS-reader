package com.danil.kleshchin.rss.domain.entity

data class Feed(
    val title: String,
    val description: String,
    val pageUrl: String,
    val author: String,
    val dateCreated: Long,
    val dateUpdated: Long,
    val kicker: String,
    val thumbUrl: String,
    val iconUrl: String,
    val iconCaption: String,
    val iconCopyright: String,
    var isFavorite: Boolean = false
) {
    val id = hashCode()

    /**
     * Unfortunately, the API doesn't send feeds with ids. So, use the hashCode as id.
     * Don't use the [dateUpdated] and [isFavorite] fields in the [equals] and [hashCode] methods.
     */
    override fun hashCode(): Int {
        var result = 31 * title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + pageUrl.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + dateCreated.hashCode()
        result = 31 * result + kicker.hashCode()
        result = 31 * result + thumbUrl.hashCode()
        result = 31 * result + iconUrl.hashCode()
        result = 31 * result + iconCaption.hashCode()
        result = 31 * result + iconCopyright.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || javaClass != other.javaClass) {
            return false
        }
        if (other !is Feed) {
            return false
        }
        if (other.title != title) {
            return false
        }
        if (other.description != description) {
            return false
        }
        if (other.pageUrl != pageUrl) {
            return false
        }
        if (other.author != author) {
            return false
        }
        if (other.dateCreated != dateCreated) {
            return false
        }
        if (other.kicker != kicker) {
            return false
        }
        if (other.thumbUrl != thumbUrl) {
            return false
        }
        if (other.iconUrl != iconUrl) {
            return false
        }
        if (other.iconCaption != iconCaption) {
            return false
        }
        return other.iconCopyright == iconCopyright
    }
}
