package com.example.randomcityapp.core

import kotlinx.coroutines.flow.Flow

interface RandomCityRepo {

    fun loadAll(): Flow<List<RandomCity>>
}