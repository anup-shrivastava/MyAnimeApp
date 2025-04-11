package com.anime.myanimeapp.application

import android.app.Application
import com.anime.myanimeapp.utils.NetworkObserver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyAnimeApplication: Application() {
    @Inject
    lateinit var networkChangeMonitor: NetworkObserver
    override fun onCreate() {
        super.onCreate()
    }
}