package com.example.randomcityapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.randomcityapp.core.models.RandomCity
import java.time.LocalDateTime

@Entity(tableName = "random_city")
data class RandomCityEntity(
    @PrimaryKey
    val emissionDateTime: LocalDateTime,
    val name: String,
    val color: String
) {

    fun toModel() = RandomCity(name, color, emissionDateTime)

    companion object {

        fun fromModel(model: RandomCity) = RandomCityEntity(
            emissionDateTime = model.emissionDateTime,
            name = model.name,
            color = model.color,
        )
    }
}