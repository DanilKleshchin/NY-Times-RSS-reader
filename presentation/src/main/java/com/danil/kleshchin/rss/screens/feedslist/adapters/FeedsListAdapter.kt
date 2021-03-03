package com.danil.kleshchin.rss.screens.feedslist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.rss.R
import com.danil.kleshchin.rss.databinding.ItemFeedListBinding
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.utils.getElapsedTimeFromCurrentTime
import com.danil.kleshchin.rss.utils.getTimeStampFromDateTime
import com.squareup.picasso.Picasso

class FeedsListAdapter(
    private val feedList: List<Feed>,
    private val context: Context,
    private val feedClickListener: OnFeedClickListener
) : RecyclerView.Adapter<FeedsListAdapter.FeedListViewHolder>() {

    interface OnFeedClickListener {
        fun onFeedClick(feed: Feed)
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
        private val clickListener: OnFeedClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun getBinding() = binding

        fun bind(feed: Feed) {
            binding.apply {
                title.text = feed.title
                description.text = feed.description
                author.text = feed.author

                val timeStamp = getTimeStampFromDateTime(feed.dateCreated)
                datetime.text = getElapsedTimeFromCurrentTime(timeStamp)

                if (feed.thumbUrl.isEmpty()) {
                    thumb.setImageResource(R.drawable.ic_empty_feed_icon)
                } else {
                    Picasso.get().load(feed.thumbUrl).into(thumb)
                }

                root.setOnClickListener { clickListener.onFeedClick(feed) }
            }
        }
    }
}
