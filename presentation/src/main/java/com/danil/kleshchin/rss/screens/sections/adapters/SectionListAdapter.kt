package com.danil.kleshchin.rss.screens.sections.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.rss.databinding.ItemSectionListBinding
import com.danil.kleshchin.rss.entities.section.SectionEntity

class SectionListAdapter(
    private val context: Context,
    private val onSectionClick: ((sectionEntity: SectionEntity) -> Unit)
) : RecyclerView.Adapter<SectionListAdapter.SectionListViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<SectionEntity>() {
        override fun areItemsTheSame(oldItem: SectionEntity, newItem: SectionEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SectionEntity, newItem: SectionEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var sectionList: List<SectionEntity>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount(): Int = sectionList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SectionListViewHolder {
        val binding = ItemSectionListBinding.inflate(LayoutInflater.from(context), parent, false)
        return SectionListViewHolder(binding, onSectionClick)
    }

    override fun onBindViewHolder(
        holder: SectionListViewHolder,
        position: Int
    ) {
        holder.bind(sectionList[position])
    }

    class SectionListViewHolder(
        private val binding: ItemSectionListBinding,
        private val onSectionClick: ((sectionEntity: SectionEntity) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(section: SectionEntity) {
            binding.apply {
                this.section = section
                setClickListener { onSectionClick(section) }
            }
        }
    }
}
