package com.rohit.whatsappclone

import android.app.Application
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        val config = mapOf(
            "cloud_name" to "drlfckuyr",
            "api_key" to "878152721933937",
            "api_secret" to "EaUu4VgTCBmLghY7KJ7f9NI3SBA"
        )
        MediaManager.init(this, config)
    }
}