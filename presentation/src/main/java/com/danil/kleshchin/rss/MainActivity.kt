package com.danil.kleshchin.rss

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.danil.kleshchin.rss.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NYTimesRSSFeeds)
        setContentView<MainActivityBinding>(this, R.layout.main_activity)
    }
}
