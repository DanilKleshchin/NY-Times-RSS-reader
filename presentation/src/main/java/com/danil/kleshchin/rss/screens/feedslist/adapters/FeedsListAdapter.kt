package com.danil.kleshchin.rss.screens.feedslist.adapters

import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danil.kleshchin.rss.databinding.ItemFeedListBinding
import com.danil.kleshchin.rss.databinding.ItemVisitSiteBinding
import com.danil.kleshchin.rss.entities.feed.FeedEntity

class FeedsListAdapter(
    private val context: Context,
    private val feedClickListener: OnFeedClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnFeedClickListener {
        fun onFeedClick(feed: FeedEntity)

        fun onFeedImageClick(feed: FeedEntity)

        fun onStarClick(feed: FeedEntity)

        fun onShareClick(feed: FeedEntity)
    }

    private val TYPE_FEED = 0
    private val TYPE_VISIT_SITE = 1

    private val diffCallback = object : DiffUtil.ItemCallback<FeedEntity>() {
        override fun areItemsTheSame(oldItem: FeedEntity, newItem: FeedEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FeedEntity, newItem: FeedEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var feedList: List<FeedEntity>
        get() = differ.currentList
        set(value) { differ.submitList(value) }


    override fun getItemCount(): Int = feedList.size + 1 //Plus one for the Visit NYTimes site item

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            in (feedList.indices) ->
                TYPE_FEED
            else ->
                TYPE_VISIT_SITE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_VISIT_SITE -> {
                val binding =
                    ItemVisitSiteBinding.inflate(LayoutInflater.from(context), parent, false)
                VisitSiteViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemFeedListBinding.inflate(LayoutInflater.from(context), parent, false)
                FeedListViewHolder(binding, feedClickListener)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is FeedListViewHolder) {
            holder.bind(feedList[position])
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is FeedListViewHolder) {
            holder.getBinding().thumb.let {
                Glide.with(context).clear(it)
            }
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
                thumb.setOnClickListener { feedClickListener.onFeedImageClick(feed) }
            }
        }
    }

    class VisitSiteViewHolder(binding: ItemVisitSiteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.visitText.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
