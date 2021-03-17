package com.danil.kleshchin.rss.screens.favorites.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.ItemFeedListBinding
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


class FavoriteFeedsListAdapter(
    private val feedList: ArrayList<FeedEntity>,
    private val context: Context,
    private val feedClickListener: OnFeedClickListener
) : RecyclerView.Adapter<FavoriteFeedsListAdapter.FavoriteFeedsListViewHolder>() {

    interface OnFeedClickListener {
        fun onFeedClick(feed: FeedEntity)

        fun onStarClick(viewHolder: RecyclerView.ViewHolder, feed: FeedEntity)

        fun onFeedUndoDismissed(feed: FeedEntity)

        fun onShareClick(feed: FeedEntity)
    }

    private var feedToRemove: FeedEntity? = null
    private var feedToRemovePosition:Int = 0

    override fun getItemCount(): Int = feedList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteFeedsListViewHolder {
        val binding = ItemFeedListBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavoriteFeedsListViewHolder(binding, feedClickListener)
    }

    override fun onBindViewHolder(
        holder: FavoriteFeedsListViewHolder,
        position: Int
    ) {
        holder.bind(feedList[position])
    }

    override fun onViewRecycled(holder: FavoriteFeedsListViewHolder) {
        holder.getBinding().thumb.let {
            Picasso.get().cancelRequest(it)
        }
        super.onViewRecycled(holder)
    }

    fun removeFeed(viewHolder: RecyclerView.ViewHolder, recyclerView: RecyclerView) {
        val position = viewHolder.adapterPosition
        feedToRemove = feedList[position]
        feedToRemovePosition = position
        feedList.removeAt(position)
        notifyItemRemoved(position)
        showUndoSnackbar(recyclerView)
    }

    private fun showUndoSnackbar(recyclerView: RecyclerView) {
        val snackbar: Snackbar = Snackbar.make(
            recyclerView, context.getString(R.string.undo_snack_bar_title),
            Snackbar.LENGTH_LONG
        )
        snackbar
            .setAction(context.getString(R.string.undo_snackbar_undo_text)) {
            undoRemoving(
                recyclerView
            )
        }
            .addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    feedToRemove?.let { feedClickListener.onFeedUndoDismissed(it) }
                }
            })
        snackbar.show()
    }

    private fun undoRemoving(recyclerView: RecyclerView) {
        feedToRemove?.let {
            feedList.add(
                feedToRemovePosition,
                it
            )
        }
        notifyItemInserted(feedToRemovePosition)
        recyclerView.scrollToPosition(feedToRemovePosition)
    }

    class FavoriteFeedsListViewHolder(
        private val binding: ItemFeedListBinding,
        private val feedClickListener: OnFeedClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun getBinding() = binding

        fun bind(feed: FeedEntity) {
            binding.apply {
                this.feed = feed
                setClickListener { feedClickListener.onFeedClick(feed) }
                iconStar.setOnClickListener {
                    feedClickListener.onStarClick(this@FavoriteFeedsListViewHolder, feed)
                }
                iconShare.setOnClickListener { feedClickListener.onShareClick(feed) }
            }
        }
    }
}
