package com.example.randomcityapp.core.logic

import com.example.randomcityapp.core.interfaces.RandomCityRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class RandomCityProducer(
    private val randomCityGenerator: RandomCityGenerator,
    private val randomCityRepo: RandomCityRepo
) {

    private val firstRandomCityEmitted = MutableStateFlow(false)
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

    suspend fun waitForFirstEmitted() {
        firstRandomCityEmitted.first { it }
    }

    private fun launchEmitLoop() = GlobalScope.launch {
        delay(EMIT_INTERVAL)
        emitNextRandomCity()
        firstRandomCityEmitted.emit(true)
        while (isActive) {
            delay(EMIT_INTERVAL)
            emitNextRandomCity()
        }
    }

    private suspend fun emitNextRandomCity() {
        randomCityRepo.addNew(randomCityGenerator.randomCity)
    }

    companion object {
        private const val EMIT_INTERVAL = 5_000L
    }
}