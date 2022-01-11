package com.thusee.tandemlisting.usecase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thusee.tandemlisting.data.Data
import com.thusee.tandemlisting.data.network.ApiService
import com.thusee.tandemlisting.usecase.model.CommunityDataMapper
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class CommunityRemoteRepository:
    PagingSource<Int, CommunityDataMapper>(), KoinComponent {

    private val apiService: ApiService by inject()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommunityDataMapper> {
        return try {
            val nextPageNumber = params.key ?: 1
            Timber.d("Next Page number : $nextPageNumber")
            val response = apiService.fetchCommunityData(nextPageNumber)
            Timber.d("Response is : $response")
            val data: List<CommunityDataMapper> =
                generateRemoteDataList(response.data, nextPageNumber)
            Timber.d("Response Data : $data")
            LoadResult.Page(data = data, prevKey = params.key, nextKey = nextPageNumber + 1)
        } catch (exception: Exception) {
            Timber.d("Exception is : ${exception.message}")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CommunityDataMapper>): Int? {
        return state.anchorPosition
    }

    private fun generateRemoteDataList(list: List<Data>, page: Int): List<CommunityDataMapper> {
        val result = arrayListOf<CommunityDataMapper>()
        list.forEachIndexed { index, item ->
            val native = item.natives.firstOrNull() ?: ""
            val learn = item.learns.firstOrNull() ?: ""

            result.add(
                CommunityDataMapper(
                    id = page * 100 + index,
                    firstName = item.firstName,
                    topic = item.topic,
                    pictureUrl = item.pictureUrl,
                    natives = native,
                    learns = learn,
                    referenceCnt = item.referenceCnt,
                    isLike = false
                )
            )
        }

        return result.toList()
    }
}