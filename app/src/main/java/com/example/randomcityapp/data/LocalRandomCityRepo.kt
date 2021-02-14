package com.example.randomcityapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.randomcityapp.core.models.RandomCity
import com.example.randomcityapp.core.interfaces.RandomCityRepo
import kotlinx.coroutines.flow.Flow

class LocalRandomCityRepo : RandomCityRepo {

    private val randomCities: MutableLiveData<MutableList<RandomCity>> = MutableLiveData(
        mutableListOf()
    )

    override fun loadAll(): Flow<List<RandomCity>> {
        return randomCities.asFlow()
    }

    override suspend fun addNew(randomCity: RandomCity) {
        val state = randomCities.value
        state!!.add(randomCity)
        randomCities.postValue(state)
    }

}