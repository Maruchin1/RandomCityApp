package com.example.randomcityapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RandomCityDao {

    @Query("SELECT * FROM random_city")
    fun loadAll(): Flow<List<RandomCityEntity>>

    @Insert
    suspend fun insert(randomCity: RandomCityEntity)
}