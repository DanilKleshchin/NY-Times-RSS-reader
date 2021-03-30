package com.danil.kleshchin.rss.screens.favorites.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danil.kleshchin.rss.databinding.ItemFeedListBinding
import com.danil.kleshchin.rss.entities.feed.FeedEntity


class FavoriteFeedsListAdapter(
    private val context: Context,
    private val onFeedClick: ((feed: FeedEntity) -> Unit),
    private val onFeedImageClick: ((feed: FeedEntity) -> Unit),
    private val onStarClick: ((position: Int) -> Unit),
    private val onShareClick: ((feed: FeedEntity) -> Unit),
) : RecyclerView.Adapter<FavoriteFeedsListAdapter.FavoriteFeedsListViewHolder>() {

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

    override fun getItemCount(): Int = feedList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteFeedsListViewHolder {
        val binding = ItemFeedListBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavoriteFeedsListViewHolder(
            binding,
            onFeedClick,
            onFeedImageClick,
            onStarClick,
            onShareClick
        )
    }

    override fun onBindViewHolder(
        holder: FavoriteFeedsListViewHolder,
        position: Int
    ) {
        holder.bind(feedList[position])
    }

    override fun onViewRecycled(holder: FavoriteFeedsListViewHolder) {
        holder.getBinding().thumb.let {
            Glide.with(context).clear(it)
        }
        super.onViewRecycled(holder)
    }

    class FavoriteFeedsListViewHolder(
        private val binding: ItemFeedListBinding,
        private val onFeedClick: ((feed: FeedEntity) -> Unit),
        private val onFeedImageClick: ((feed: FeedEntity) -> Unit),
        private val onStarClick: ((position: Int) -> Unit),
        private val onShareClick: ((feed: FeedEntity) -> Unit),
    ) : RecyclerView.ViewHolder(binding.root) {

        fun getBinding() = binding

        fun bind(feed: FeedEntity) {
            binding.apply {
                this.feed = feed
                setClickListener { onFeedClick(feed) }
                iconShare.setOnClickListener { onShareClick(feed) }
                thumb.setOnClickListener { onFeedImageClick(feed) }
                iconStar.setOnClickListener { onStarClick(adapterPosition) }
            }
        }
    }
}
