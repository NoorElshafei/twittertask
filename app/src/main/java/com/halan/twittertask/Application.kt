package com.halan.twittertask

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        installTimber()
    }



    private fun installTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
