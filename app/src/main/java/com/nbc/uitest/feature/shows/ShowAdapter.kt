package com.nbc.uitest.feature.shows

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.nbc.uitest.databinding.RowShowBinding
import com.nbc.uitest.Show

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