package com.example.randomcityapp.core

import com.example.randomcityapp.AppTestRule
import com.example.randomcityapp.core.logic.RandomCityGenerator
import com.example.randomcityapp.core.logic.SystemUtil
import com.example.randomcityapp.core.models.RandomCity
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class RandomCityGeneratorTest {
    private val systemUtil: SystemUtil = mockk()

    private lateinit var randomCityGenerator: RandomCityGenerator

    @get:Rule
    val appTestRule = AppTestRule()

    @Before
    fun before() {
        randomCityGenerator =
            RandomCityGenerator(systemUtil)
    }

    @Test
    fun getRandomCity() {
        val currentDateTime = LocalDateTime.of(2021, 2, 13, 13, 0, 0)
        every { systemUtil.getRandomInt(any()) } returns 2
        every { systemUtil.getCurrentDateTime() } returns currentDateTime

        val randomCity = randomCityGenerator.randomCity

        assertThat(randomCity).isEqualTo(
            RandomCity(
                name = "Poznań",
                color = "Blue",
                emissionDateTime = currentDateTime
            )
        )
    }

}