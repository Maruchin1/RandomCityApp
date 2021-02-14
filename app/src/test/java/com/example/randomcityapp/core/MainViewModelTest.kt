package com.example.randomcityapp.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.randomcityapp.AppTestRule
import com.example.randomcityapp.await
import com.example.randomcityapp.core.interfaces.CityLocationApi
import com.example.randomcityapp.core.interfaces.RandomCityRepo
import com.example.randomcityapp.core.models.RandomCity
import com.example.randomcityapp.core.view_models.MainViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class MainViewModelTest {
    private val randomCitiesState: MutableStateFlow<List<RandomCity>> = MutableStateFlow(listOf())
    private val randomCityRepo: RandomCityRepo = mockk()
    private val cityLocationApi: CityLocationApi = mockk()

    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    val appTestRule = AppTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        every { randomCityRepo.loadAll() } returns randomCitiesState

        mainViewModel = MainViewModel(randomCityRepo, cityLocationApi)
    }

    @Test
    fun randomCities_InitialLoad() = runBlocking {
        val cities = mainViewModel.randomCities.await()

        assertThat(cities).isEmpty()
    }

    @Test
    fun randomCities_CitesEmitted() = runBlocking {
        val firstCitiesFromRepo = listOf(
            RandomCity(name = "Gdańsk", color = "Yellow", emissionDateTime = LocalDateTime.now()),
            RandomCity(name = "Warszawa", color = "Green", emissionDateTime = LocalDateTime.now())
        )
        val secondCitiesFromRepo = listOf(
            RandomCity(name = "Gdańsk", color = "Yellow", emissionDateTime = LocalDateTime.now()),
            RandomCity(name = "Warszawa", color = "Green", emissionDateTime = LocalDateTime.now()),
            RandomCity(name = "Poznań", color = "Blue", emissionDateTime = LocalDateTime.now()),
        )

        randomCitiesState.emit(firstCitiesFromRepo)
        val firstCities = mainViewModel.randomCities.await()
        randomCitiesState.emit(secondCitiesFromRepo)
        val secondCities = mainViewModel.randomCities.await()

        assertThat(firstCities).isEqualTo(listOf(
            firstCitiesFromRepo[0],
            firstCitiesFromRepo[1]
        ))
        assertThat(secondCities).isEqualTo(listOf(
            secondCitiesFromRepo[0],
            secondCitiesFromRepo[2],
            secondCitiesFromRepo[1]
        ))
    }
}