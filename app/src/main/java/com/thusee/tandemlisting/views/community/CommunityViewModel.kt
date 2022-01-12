package com.thusee.tandemlisting.views.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thusee.tandemlisting.usecase.CommunityRemoteRepository
import com.thusee.tandemlisting.usecase.LikeStatusRepo
import com.thusee.tandemlisting.usecase.model.CommunityDataMapper
import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent
import org.koin.core.inject

class CommunityViewModel: ViewModel(), KoinComponent {

    private val fetchRemoteDataRepository: CommunityRemoteRepository by inject()
    private val likeStatusRepo: LikeStatusRepo by inject()

    init {
        likeStatusRepo.get()
    }

    fun fetchRemoteDataList(): Flow<PagingData<CommunityDataMapper>> {

        return Pager(PagingConfig(pageSize = 1)) {
            fetchRemoteDataRepository
        }.flow.cachedIn(viewModelScope)

    }

    fun like(dataMapper: CommunityDataMapper) {
        when (likeStatusRepo.check(dataMapper)) {
            true -> {
                likeStatusRepo.remove(dataMapper.id)
            }
            false -> {
                likeStatusRepo.add(dataMapper.id)
            }
        }
    }

}