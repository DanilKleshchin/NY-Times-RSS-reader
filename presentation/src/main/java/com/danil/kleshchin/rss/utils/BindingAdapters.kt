package com.danil.kleshchin.rss.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.danil.kleshchin.rss.R


@BindingAdapter("load_image")
fun bindLoadImage(view: ImageView, imageUrl: String) {
    if (imageUrl.isEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        Glide.with(view.context).load(imageUrl).into(view)
    }
}

@BindingAdapter("load_image")
fun bindLoadImage(view: ImageView, @DrawableRes imageId: Int) {
    view.setImageResource(imageId)
}

@BindingAdapter("android:html_text")
fun bindHtmlText(textView: TextView, text: String) {
    textView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

@BindingAdapter("android:src")
fun bindFavorite(imageView: ImageView, isFavorite: Boolean) {
    imageView.setImageResource(if (isFavorite) {
        R.drawable.ic_star_checked
    } else {
        R.drawable.ic_star_unchecked
    })
}
