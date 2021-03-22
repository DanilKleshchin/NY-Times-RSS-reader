package com.danil.kleshchin.rss.screens.feedimage

import android.content.ContentResolver
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.danil.kleshchin.rss.utils.saveImage


class FeedImageViewModel : ViewModel() {

    //TODO add checking for downloaded images. Don't save the image more than once
    fun saveImageToGallery(bitmap: Bitmap, contentResolver: ContentResolver): Boolean {
        return saveImage(bitmap, contentResolver)
    }
}
