package com.danil.kleshchin.rss.utils

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("load_image")
fun bindLoadImage(view: View, imageUrl: String) {
    if (imageUrl.isEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        Picasso.get().load(imageUrl).into(view as ImageView)
    }
}

@BindingAdapter("load_image")
fun bindLoadImage(view: View, @DrawableRes imageId: Int) {
    Picasso.get().load(imageId).into(view as ImageView)
}
