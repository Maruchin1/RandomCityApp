package com.example.randomcityapp.core.view_models

import androidx.lifecycle.*
import com.example.randomcityapp.core.models.RandomCity
import com.example.randomcityapp.core.interfaces.CityLocationApi
import com.example.randomcityapp.core.interfaces.RandomCityRepo
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val randomCityRepo: RandomCityRepo,
    private val cityLocationApi: CityLocationApi
) : ViewModel() {

    private val _detailsCity = MutableLiveData<RandomCity?>(null)

    val randomCities: LiveData<List<RandomCity>> = randomCityRepo.loadAll()
        .map { sortCitiesAlphabetically(it) }
        .asLiveData()

    val detailsCity: LiveData<RandomCity?> = _detailsCity

    val detailsCityLocation: LiveData<LatLng?> = _detailsCity.switchMap { loadCityLocation(it) }

    fun selectDetailsCity(randomCity: RandomCity) {
        _detailsCity.value = randomCity
    }

    fun clearDetailsCity() {
        _detailsCity.value = null
    }

    private fun sortCitiesAlphabetically(cities: List<RandomCity>): List<RandomCity> {
        return cities.sortedBy { it.name }
    }

    private fun loadCityLocation(randomCity: RandomCity?): LiveData<LatLng?> = liveData {
        val location = randomCity?.let { cityLocationApi.getCityLocation(it.name) }
        emit(location)
    }
}