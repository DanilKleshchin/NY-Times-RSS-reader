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

    fun loadFavoriteFeedsList() : LiveData<List<FeedEntity>> {
        return liveData {
            val favoritesList = getFavoriteFeedsListUseCase.execute(Unit)
            val currentTime = System.currentTimeMillis()
            emit(
                mapper.transform(favoritesList, currentTime, resourceHelper.getAndroidResources())
            )
        }
    }
}
