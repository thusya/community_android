package com.thusee.tandemlisting.views.community.event

import com.thusee.tandemlisting.usecase.model.CommunityDataMapper

interface LikeButtonClickListener {
    fun likeButtonClick(dataMapper: CommunityDataMapper)
}