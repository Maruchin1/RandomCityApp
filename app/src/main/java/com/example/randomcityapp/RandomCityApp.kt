package com.example.randomcityapp

import android.app.Application
import com.example.randomcityapp.core.logic.RandomCityProducer
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RandomCityApp : Application() {

    private val randomCityProducer: RandomCityProducer by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(coreModule, dataModule)
        }

        randomCityProducer.startEmitting()
    }
}