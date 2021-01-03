package com.danil.kleshchin.rss.screens.feeds.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.rss.databinding.ItemFeedListBinding
import com.danil.kleshchin.rss.domain.entity.Feed

class FeedListAdapter(
    private val feedList: List<Feed>,
    private val context: Context,
    private val feedClickListener: OnFeedClickListener
) : RecyclerView.Adapter<FeedListAdapter.FeedListViewHolder>() {

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

    class FeedListViewHolder(
        private val binding: ItemFeedListBinding,
        private val clickListener: OnFeedClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feed: Feed) {
            binding.apply {
                title.text = feed.title
                description.text = feed.description
                author.text = feed.author
                datetime.text = feed.date
                //TODO load feed icon
                root.setOnClickListener { clickListener.onFeedClick(feed) }
            }
        }
    }
}
