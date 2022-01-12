package com.thusee.tandemlisting.di

import android.content.Context
import android.content.SharedPreferences
import com.thusee.tandemlisting.data.network.ApiService
import com.thusee.tandemlisting.usecase.CommunityRemoteRepository
import com.thusee.tandemlisting.usecase.LikeStatusRepo
import com.thusee.tandemlisting.views.community.CommunityViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModules = module {

    viewModel { CommunityViewModel() }

    factory { CommunityRemoteRepository() }

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            "SharedPref",
            Context.MODE_PRIVATE
        )
    }

    single { LikeStatusRepo() }

    single<ApiService> {

        Retrofit.Builder()
            .baseUrl("https://tandem2019.web.app/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ApiService::class.java)
    }
}