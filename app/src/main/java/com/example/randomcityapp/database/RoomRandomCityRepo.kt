package com.example.randomcityapp.database

import com.example.randomcityapp.core.interfaces.RandomCityRepo
import com.example.randomcityapp.core.models.RandomCity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomRandomCityRepo(
    private val randomCityDao: RandomCityDao
) : RandomCityRepo {

    override fun loadAll(): Flow<List<RandomCity>> {
        return randomCityDao.loadAll().map { it.toModel() }
    }

    override suspend fun addNew(randomCity: RandomCity) {
        randomCityDao.insert(RandomCityEntity.fromModel(randomCity))
    }

    private fun List<RandomCityEntity>.toModel(): List<RandomCity> {
        return this.map { it.toModel() }
    }
}