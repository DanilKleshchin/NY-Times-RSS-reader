package com.danil.kleshchin.rss.screens.sections.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danil.kleshchin.rss.databinding.ItemSectionListBinding
import com.danil.kleshchin.rss.domain.entity.Section

class SectionListAdapter(
    private val sectionList: List<Section>,
    private val context: Context,
    private val sectionClickListener: OnSectionClickListener
) : RecyclerView.Adapter<SectionListAdapter.SectionListViewHolder>() {

    interface OnSectionClickListener {
        fun onSectionClick(section: Section)
    }

    override fun getItemCount(): Int = sectionList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SectionListViewHolder {
        val binding = ItemSectionListBinding.inflate(LayoutInflater.from(context), parent, false)
        return SectionListViewHolder(binding, sectionClickListener)
    }

    override fun onBindViewHolder(
        holder: SectionListViewHolder,
        position: Int
    ) {
        holder.bind(sectionList[position])
    }

    class SectionListViewHolder(
        private val binding: ItemSectionListBinding,
        private val clickListener: OnSectionClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(section: Section) {
            binding.apply {
                sectionName.text = section.displayName
                container.setOnClickListener { clickListener.onSectionClick(section) }
            }
        }
    }
}
