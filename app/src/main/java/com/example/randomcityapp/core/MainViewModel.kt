package com.example.randomcityapp.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class MainViewModel(
    private val randomCityRepo: RandomCityRepo
) : ViewModel() {

    val randomCities: LiveData<List<RandomCity>> = randomCityRepo.loadAll().asLiveData()
}