package com.example.mediclient.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.example.mediclient.databinding.ItemHomeContentBinding

class HomeContentViewHolder(private val binding: ItemHomeContentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(HomeContentData: HomeContent) {
        binding.ivItemHomeMeditation.setImageResource(HomeContentData.profileImage)
        binding.tvItemHomeMeditationTitle.text = HomeContentData.title
        binding.tvItemHomeWriter.text = HomeContentData.writer
    }
}
