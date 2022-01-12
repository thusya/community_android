package com.thusee.tandemlisting.usecase

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thusee.tandemlisting.usecase.model.CommunityDataMapper
import org.koin.core.KoinComponent
import org.koin.core.inject

class LikeStatusRepo: KoinComponent {

    private val sharedPreferences: SharedPreferences by inject()

    private var likes = arrayListOf<Int>()

    fun add(id: Int) {
        likes.add(id)
        save()
    }

    fun remove(id: Int) {
        likes.remove(id)
        save()
    }

    fun check(dataMapper: CommunityDataMapper): Boolean {
        return likes.firstOrNull { dataMapper.id == it } != null
    }

    fun save() {
        val editor = sharedPreferences.edit()
        editor.putString("Test", Gson().toJson(likes)).apply()
    }

    fun get(): List<Int> {
        val list = sharedPreferences.getString("Test", "")
        val type = object: TypeToken<List<Int>>() {}.type
        likes.clear()
        likes.addAll(Gson().fromJson(list, type))
        return likes
    }

}