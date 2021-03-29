package com.danil.kleshchin.rss.screens.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.GetFavoriteFeedsListUseCase
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.screens.BaseFeedViewModel
import javax.inject.Inject

class FavoriteFeedsListViewModel : BaseFeedViewModel() {

    @Inject
    lateinit var getFavoriteFeedsListUseCase: GetFavoriteFeedsListUseCase

    lateinit var feedList: ArrayList<FeedEntity>
    var feedToRemove: FeedEntity? = null
    var feedToRemovePosition: Int = -1

    fun loadFavoriteFeedsList(): LiveData<List<FeedEntity>> {
        return liveData {
            val favoritesList = getFavoriteFeedsListUseCase.execute(Unit)
            val currentTime = System.currentTimeMillis()
            feedList = (favoritesList.map {
                mapper.transform(it, currentTime, resourceHelper.getAndroidResources())
            }
                    ) as ArrayList
            emit(
                feedList
            )
        }
    }

    fun removeFeed(position: Int) {
        feedToRemove = feedList[position]
        feedToRemovePosition = position
        feedList.removeAt(position)
        feedToRemove?.let {
            addRemoveFavoriteFeed(it)
        }
    }

    fun undoFeedRemoving() {
        feedToRemove?.let { feed ->
            undoFeedRemoving(feed)
            feedList.add(feedToRemovePosition, feed)
        }
    }
}
