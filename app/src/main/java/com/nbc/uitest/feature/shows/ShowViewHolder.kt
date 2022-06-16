package com.nbc.uitest.feature.shows

import androidx.recyclerview.widget.RecyclerView
import com.nbc.uitest.Show
import com.nbc.uitest.databinding.RowShowBinding

class ShowViewHolder(private val binding: RowShowBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(show: Show) {
        binding.title.text = show.title
    }
}