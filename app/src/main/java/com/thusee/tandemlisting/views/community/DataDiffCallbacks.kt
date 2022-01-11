package com.thusee.tandemlisting.views.community

import androidx.recyclerview.widget.DiffUtil
import com.thusee.tandemlisting.usecase.model.CommunityDataMapper

object DataDiffCallbacks: DiffUtil.ItemCallback<CommunityDataMapper>() {
    override fun areItemsTheSame(oldItem: CommunityDataMapper, newItem: CommunityDataMapper): Boolean {
        return oldItem.firstName == newItem.firstName
    }

    override fun areContentsTheSame(oldItem: CommunityDataMapper, newItem: CommunityDataMapper): Boolean {
        return oldItem == newItem
    }

}