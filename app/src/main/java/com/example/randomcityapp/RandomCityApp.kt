package com.example.randomcityapp

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.randomcityapp.core.logic.RandomCityProducer
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RandomCityApp : Application(), LifecycleObserver {

    private val randomCityProducer: RandomCityProducer by inject()

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(coreModule, databaseModule, apiModule)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        randomCityProducer.startEmitting()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        randomCityProducer.stopEmitting()
    }
}