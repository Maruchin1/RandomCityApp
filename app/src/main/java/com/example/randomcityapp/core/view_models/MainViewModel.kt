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
    private val _displayMode = MutableLiveData<DisplayMode>()

    val randomCities: LiveData<List<RandomCity>> = randomCityRepo.loadAll()
        .map { sortCitiesAlphabetically(it) }
        .asLiveData()

    val detailsCity: LiveData<RandomCity?> = _detailsCity

    val detailsCityLocation: LiveData<LatLng?> = _detailsCity.switchMap { loadCityLocation(it) }

    val displayMode: LiveData<DisplayMode> = _displayMode

    fun selectDetailsCity(randomCity: RandomCity) {
        _detailsCity.value = randomCity
    }

    fun setDisplayMode(displayMode: DisplayMode) {
        _displayMode.value = displayMode
    }

    fun getDisplayMode(): DisplayMode? {
        return _displayMode.value
    }

    fun isDetailsCitySelected(): Boolean {
        return _detailsCity.value != null
    }

    private fun sortCitiesAlphabetically(cities: List<RandomCity>): List<RandomCity> {
        return cities.sortedBy { it.name }
    }

    private fun loadCityLocation(randomCity: RandomCity?): LiveData<LatLng?> = liveData {
        val location = randomCity?.let { cityLocationApi.getCityLocation(it.name) }
        emit(location)
    }

    enum class DisplayMode {
        STANDARD, SIDE_BY_SIDE
    }
}