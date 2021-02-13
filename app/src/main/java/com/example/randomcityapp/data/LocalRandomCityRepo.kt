package com.example.randomcityapp.data

import com.example.randomcityapp.core.RandomCity
import com.example.randomcityapp.core.RandomCityRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LocalRandomCityRepo : RandomCityRepo {

    private val randomCities: MutableStateFlow<List<RandomCity>> = MutableStateFlow(listOf(
        RandomCity(text = "Gdańsk", color = "Yellow"),
        RandomCity(text = "Warszawa", color = "Green"),
        RandomCity(text = "Poznań", color = "Blue"),
        RandomCity(text = "Białystok", color = "Red"),
        RandomCity(text = "Wrocław", color = "Black"),
        RandomCity(text = "Katowice", color = "White"),
    ))

    override fun loadAll(): Flow<List<RandomCity>> {
        return randomCities
    }

}