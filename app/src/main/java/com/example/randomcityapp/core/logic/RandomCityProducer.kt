package com.example.randomcityapp.core.logic

import com.example.randomcityapp.core.interfaces.RandomCityRepo
import kotlinx.coroutines.*

class RandomCityProducer(
    private val randomCityGenerator: RandomCityGenerator,
    private val randomCityRepo: RandomCityRepo
) {

    private var emitJob: Job? = null

    fun startEmitting() {
        if (emitJob == null) {
            emitJob = launchEmitLoop()
        }
    }

    fun stopEmitting() {
        emitJob?.cancel()
        emitJob = null
    }

    private fun launchEmitLoop() = GlobalScope.launch {
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