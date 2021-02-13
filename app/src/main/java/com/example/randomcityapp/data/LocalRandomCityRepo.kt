package com.example.randomcityapp.data

import com.example.randomcityapp.core.RandomCity
import com.example.randomcityapp.core.RandomCityRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

class LocalRandomCityRepo : RandomCityRepo {

    private val randomCities: MutableStateFlow<List<RandomCity>> = MutableStateFlow(listOf(
        RandomCity(text = "Gdańsk", color = "Yellow", emissionDateTime = LocalDateTime.now()),
        RandomCity(text = "Warszawa", color = "Green", emissionDateTime = LocalDateTime.now()),
        RandomCity(text = "Poznań", color = "Blue", emissionDateTime = LocalDateTime.now()),
        RandomCity(text = "Białystok", color = "Red", emissionDateTime = LocalDateTime.now()),
        RandomCity(text = "Wrocław", color = "Black", emissionDateTime = LocalDateTime.now()),
        RandomCity(text = "Katowice", color = "White", emissionDateTime = LocalDateTime.now()),
    ))

    override fun loadAll(): Flow<List<RandomCity>> {
        return randomCities
    }

}