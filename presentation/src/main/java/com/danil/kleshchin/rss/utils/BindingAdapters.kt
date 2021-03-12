package com.danil.kleshchin.rss.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("load_image")
fun bindLoadImage(view: ImageView, imageUrl: String) {
    if (imageUrl.isEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        Picasso.get().load(imageUrl).into(view)
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
