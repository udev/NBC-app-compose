package com.nbc.uitest

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.nbc.uitest.databinding.RowShowBinding

class ShowAdapter : ListAdapter<Show, ShowViewHolder>(ShowDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val inflater = android.view.LayoutInflater.from(parent.context)
        val binding = RowShowBinding.inflate(inflater, parent, false)
        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}