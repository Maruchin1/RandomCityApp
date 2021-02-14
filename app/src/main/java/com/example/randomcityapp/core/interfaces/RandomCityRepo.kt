package com.example.randomcityapp.core.interfaces

import com.example.randomcityapp.core.models.RandomCity
import kotlinx.coroutines.flow.Flow

interface RandomCityRepo {

    fun loadAll(): Flow<List<RandomCity>>

    suspend fun addNew(randomCity: RandomCity)
}