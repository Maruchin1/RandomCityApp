package com.example.randomcityapp.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val randomCityRepo: RandomCityRepo
) : ViewModel() {

    val randomCities: LiveData<List<RandomCity>> = randomCityRepo.loadAll()
        .map { sortCitiesAlphabetically(it) }
        .asLiveData()

    private fun sortCitiesAlphabetically(cities: List<RandomCity>): List<RandomCity> {
        return cities.sortedBy { it.name }
    }
}