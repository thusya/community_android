package com.thusee.tandemlisting.data.network

import com.thusee.tandemlisting.data.CommunityResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("community_{page}.json")
    suspend fun fetchCommunityData(
        @Path("page") page: Int): CommunityResponse
}