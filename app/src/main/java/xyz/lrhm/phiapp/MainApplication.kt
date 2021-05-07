package xyz.lrhm.phiapp

import android.app.Application
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }


}