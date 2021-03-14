package com.danil.kleshchin.rss.screens.feedslist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.rss.databinding.ItemFeedListBinding
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.squareup.picasso.Picasso

class FeedsListAdapter(
    private val feedList: List<FeedEntity>,
    private val context: Context,
    private val feedClickListener: OnFeedClickListener
) : RecyclerView.Adapter<FeedsListAdapter.FeedListViewHolder>() {

    interface OnFeedClickListener {
        fun onFeedClick(feed: FeedEntity)

        fun onStarClick(feed: FeedEntity)

        fun onShareClick(feed: FeedEntity)
    }

    override fun getItemCount(): Int = feedList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedListViewHolder {
        val binding = ItemFeedListBinding.inflate(LayoutInflater.from(context), parent, false)
        return FeedListViewHolder(binding, feedClickListener)
    }

    override fun onBindViewHolder(
        holder: FeedListViewHolder,
        position: Int
    ) {
        holder.bind(feedList[position])
    }

    override fun onViewRecycled(holder: FeedListViewHolder) {
        holder.getBinding().thumb.let {
            Picasso.get().cancelRequest(it)
        }
        super.onViewRecycled(holder)
    }

    class FeedListViewHolder(
        private val binding: ItemFeedListBinding,
        private val feedClickListener: OnFeedClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun getBinding() = binding

        fun bind(feed: FeedEntity) {
            binding.apply {
                this.feed = feed
                setClickListener { feedClickListener.onFeedClick(feed) }
                iconStar.setOnClickListener { feedClickListener.onStarClick(feed) }
                iconShare.setOnClickListener { feedClickListener.onShareClick(feed) }
            }
        }
    }
}
