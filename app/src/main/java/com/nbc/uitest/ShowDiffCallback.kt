package com.nbc.uitest

import androidx.recyclerview.widget.DiffUtil

object ShowDiffCallback : DiffUtil.ItemCallback<Show>() {
    override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean = oldItem == newItem

    override fun getChangePayload(oldItem: Show, newItem: Show): Any? = null
}