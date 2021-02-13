package com.example.randomcityapp.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val randomCityRepo: RandomCityRepo
) : ViewModel() {

    val randomCities: LiveData<List<RandomCity>> = randomCityRepo.loadAll()
        .map { sortCitiesAlphabetically(it) }
        .asLiveData()

    val detailsCity: LiveData<RandomCity?>
        get() = _detailsCity

    private val _detailsCity = MutableLiveData<RandomCity?>(null)

    fun showCityDetails(randomCity: RandomCity) {
        _detailsCity.value = randomCity
    }

    fun closeCityDetails() {
        _detailsCity.value = null
    }

    private fun sortCitiesAlphabetically(cities: List<RandomCity>): List<RandomCity> {
        return cities.sortedBy { it.name }
    }
}