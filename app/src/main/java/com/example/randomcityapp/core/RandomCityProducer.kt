package com.example.randomcityapp.core

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class RandomCityProducer(
    private val randomCityGenerator: RandomCityGenerator,
    private val randomCityRepo: RandomCityRepo
) {

    fun startEmitting() = GlobalScope.launch {
        while (isActive) {
            emitNextRandomCity()
            delay(INTERVAL)
        }
    }

    private suspend fun emitNextRandomCity() {
        randomCityRepo.addNew(randomCityGenerator.randomCity)
    }

    companion object {
        private const val INTERVAL = 5_000L
    }
}