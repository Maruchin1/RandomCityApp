package com.example.randomcityapp.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.randomcityapp.MockkTestRule
import com.example.randomcityapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    private val randomCitiesState: MutableStateFlow<List<RandomCity>> = MutableStateFlow(listOf())
    private val randomCityRepo: RandomCityRepo = mockk()

    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    val mockkTestRule = MockkTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        every { randomCityRepo.loadAll() } returns randomCitiesState

        mainViewModel = MainViewModel(randomCityRepo)
    }

    @Test
    fun randomCities_InitialLoad() {
        val cities = mainViewModel.randomCities.getOrAwaitValue()

        assertThat(cities).isEmpty()
    }

    @Test
    fun randomCities_CitesEmitted() = runBlocking {
        val firstCitiesFromRepo = listOf(
            RandomCity(text = "Gdańsk", color = "Yellow"),
            RandomCity(text = "Warszawa", color = "Green")
        )
        val secondCitiesFromRepo = listOf(
            RandomCity(text = "Gdańsk", color = "Yellow"),
            RandomCity(text = "Warszawa", color = "Green"),
            RandomCity(text = "Poznań", color = "Blue"),
        )

        randomCitiesState.emit(firstCitiesFromRepo)
        val firstCities = mainViewModel.randomCities.getOrAwaitValue()
        randomCitiesState.emit(secondCitiesFromRepo)
        val secondCities = mainViewModel.randomCities.getOrAwaitValue()

        assertThat(firstCities).isEqualTo(firstCitiesFromRepo)
        assertThat(secondCities).isEqualTo(secondCitiesFromRepo)
    }
}