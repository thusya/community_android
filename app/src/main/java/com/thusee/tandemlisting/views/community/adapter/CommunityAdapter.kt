package com.thusee.tandemlisting.views.community.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thusee.tandemlisting.R
import com.thusee.tandemlisting.databinding.ItemViewBinding
import com.thusee.tandemlisting.usecase.LikeStatusRepo
import com.thusee.tandemlisting.usecase.model.CommunityDataMapper
import com.thusee.tandemlisting.views.community.event.LikeButtonClickListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class CommunityAdapter(
    private val context: Context,
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
        return CommunityAdapterHolder(binding, context, listener)
    }

    class CommunityAdapterHolder(
        private val binding: ItemViewBinding,
        private val context: Context,
        private val listener: LikeButtonClickListener
    ):
        RecyclerView.ViewHolder(binding.root), KoinComponent {

        private val likeStatusRepo: LikeStatusRepo by inject()

        fun bind(data: CommunityDataMapper) {
            binding.itemNameText.text = data.firstName
            binding.itemTopicText.text = data.topic
            binding.itemLearnText.text = data.learns
            binding.itemNativeText.text = data.natives

            if (likeStatusRepo.check(data)) {
                binding.like.setImageResource(R.drawable.like_selected)
            } else {
                binding.like.setImageResource(R.drawable.like_normal)
            }

            binding.like.setOnClickListener{
                listener.likeButtonClick(data)
            }

            when (data.referenceCnt) {
                0 -> {
                    binding.itemReferenceCntText.text = binding.root.context.getString(R.string.item_new)
                    binding.itemReferenceCntText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                    binding.itemReferenceCntText.background = binding.root.context.getDrawable(R.drawable.reference_count_bg_shape)
                }
                else -> {
                    binding.itemReferenceCntText.text = "${data.referenceCnt}"
                    binding.itemReferenceCntText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                    binding.itemReferenceCntText.setBackgroundColor(Color.TRANSPARENT)

                }
            }

            Glide.with(binding.itemViewImage.context)
                .load(data.pictureUrl)
                .fitCenter()
                .into(binding.itemViewImage)
        }

    }
}