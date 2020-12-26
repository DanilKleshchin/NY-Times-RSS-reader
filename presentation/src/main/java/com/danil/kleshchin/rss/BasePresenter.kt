package com.danil.kleshchin.rss

interface BasePresenter<T> {
    fun onAttach()
    fun onDetach()
    fun setView(view: T)
}
