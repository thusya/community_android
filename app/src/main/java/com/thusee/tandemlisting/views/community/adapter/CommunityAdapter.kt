package com.thusee.tandemlisting.views.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thusee.tandemlisting.R
import com.thusee.tandemlisting.databinding.ItemViewBinding
import com.thusee.tandemlisting.util.LikeStateUtil
import com.thusee.tandemlisting.usecase.model.CommunityDataMapper
import com.thusee.tandemlisting.views.community.event.LikeButtonClickListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class CommunityAdapter(
    diffCallback: DiffUtil.ItemCallback<CommunityDataMapper>,
    private val listener: LikeButtonClickListener
): PagingDataAdapter<CommunityDataMapper, CommunityAdapter.CommunityAdapterHolder>(diffCallback) {

    override fun onBindViewHolder(holder: CommunityAdapterHolder, position: Int) {
        val data = getItem(position)
        data?.let { item ->
            holder.bind(item)

            holder.itemView.setOnClickListener {
                listener.likeButtonClick(item)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityAdapterHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
        return CommunityAdapterHolder(binding)
    }

    class CommunityAdapterHolder(private val binding: ItemViewBinding):
        RecyclerView.ViewHolder(binding.root), KoinComponent {

        private val likeStateUtil: LikeStateUtil by inject()

        fun bind(data: CommunityDataMapper) {
            binding.itemNameText.text = data.firstName
            binding.itemTopicText.text = data.topic
            binding.itemLearnText.text = data.learns
            binding.itemNativeText.text = data.natives

            if (likeStateUtil.check(data)) {
                binding.bookMark.setImageResource(R.drawable.like_selected)
            } else {
                binding.bookMark.setImageResource(R.drawable.like_normal)
            }

            when (data.referenceCnt) {
                0 -> binding.itemReferenceCntText.text = "new"
                else -> binding.itemReferenceCntText.text = "${data.referenceCnt}"
            }

            Glide.with(binding.itemViewImage.context)
                .load(data.pictureUrl)
                .fitCenter()
                .into(binding.itemViewImage)
        }

    }
}