package com.thusee.tandemlisting.usecase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommunityDataMapper(
    val id: Int = 0,
    val topic: String = "",
    val firstName: String = "",
    val pictureUrl: String = "",
    val natives: String = "",
    val learns: String = "",
    val referenceCnt: Int = 0,
    val isLike: Boolean = false
): Parcelable
