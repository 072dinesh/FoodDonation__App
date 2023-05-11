package com.example.fooddonationapp

import android.app.Application
import com.example.fooddonationapp.utils.PrefManager

import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        PrefManager.init(this)
        Timber.plant(Timber.DebugTree())
    }
}