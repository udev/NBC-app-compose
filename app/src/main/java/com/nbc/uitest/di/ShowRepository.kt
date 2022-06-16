package com.nbc.uitest.di

import android.app.Activity
import com.google.gson.Gson
import com.nbc.uitest.model.Page
import javax.inject.Inject

class ShowRepository @Inject constructor(
    private val gson: Gson
) : ShowService {
    override suspend fun getShows(activity: Activity, id: Int): Page {
        activity.resources.openRawResource(id).let {
            return gson.fromJson(it.bufferedReader(), Page::class.java)
        }
    }
}