package com.example.randomcityapp.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.randomcityapp.AppTestRule
import com.example.randomcityapp.await
import com.example.randomcityapp.core.interfaces.CityLocationApi
import com.example.randomcityapp.core.interfaces.RandomCityRepo
import com.example.randomcityapp.core.models.RandomCity
import com.example.randomcityapp.core.view_models.MainViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
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

        assertThat(firstCities).isEqualTo(
            listOf(
                firstCitiesFromRepo[0],
                firstCitiesFromRepo[1]
            )
        )
        assertThat(secondCities).isEqualTo(
            listOf(
                secondCitiesFromRepo[0],
                secondCitiesFromRepo[2],
                secondCitiesFromRepo[1]
            )
        )
    }

    @Test
    fun detailsCity() = runBlocking {
        val selectedRandomCity = RandomCity(
            name = "Gdańsk",
            color = "Yellow",
            emissionDateTime = LocalDateTime.now()
        )

        val initialDetailsCity = mainViewModel.detailsCity.await()
        mainViewModel.selectDetailsCity(selectedRandomCity)
        val selectedDetailsCity = mainViewModel.detailsCity.await()

        assertThat(initialDetailsCity).isNull()
        assertThat(selectedDetailsCity).isEqualTo(selectedDetailsCity)
    }

    @Test
    fun isDetailsCitySelected() {
        val selectedRandomCity = RandomCity(
            name = "Gdańsk",
            color = "Yellow",
            emissionDateTime = LocalDateTime.now()
        )

        val initialIsDetailsCitySelected = mainViewModel.isDetailsCitySelected()
        mainViewModel.selectDetailsCity(selectedRandomCity)
        val detailsCitySelected = mainViewModel.isDetailsCitySelected()

        assertThat(initialIsDetailsCitySelected).isFalse()
        assertThat(detailsCitySelected).isTrue()
    }

    @Test
    fun detailsCityLocation() = runBlocking {
        val selectedRandomCity = RandomCity(
            name = "Gdańsk",
            color = "Yellow",
            emissionDateTime = LocalDateTime.now()
        )
        val cityLocationFromApi = LatLng(50.0, 100.0)
        coEvery { cityLocationApi.getCityLocation(selectedRandomCity.name) } returns cityLocationFromApi

        val initialDetailsCityLocation = mainViewModel.detailsCityLocation.await()
        mainViewModel.selectDetailsCity(selectedRandomCity)
        val selectedDetailsCityLocation = mainViewModel.detailsCityLocation.await()

        assertThat(initialDetailsCityLocation).isNull()
        assertThat(selectedDetailsCityLocation).isEqualTo(cityLocationFromApi)
    }

    @Test
    fun displayMode() = runBlocking {
        mainViewModel.setDisplayMode(MainViewModel.DisplayMode.STANDARD)
        val firstDisplayMode = mainViewModel.displayMode.await()
        mainViewModel.setDisplayMode(MainViewModel.DisplayMode.SIDE_BY_SIDE)
        val secondDisplayMode = mainViewModel.displayMode.await()

        assertThat(firstDisplayMode).isEqualTo(MainViewModel.DisplayMode.STANDARD)
        assertThat(secondDisplayMode).isEqualTo(MainViewModel.DisplayMode.SIDE_BY_SIDE)
    }

    @Test
    fun getDisplayMode() = runBlocking {
        mainViewModel.setDisplayMode(MainViewModel.DisplayMode.STANDARD)
        val firstDisplayMode = mainViewModel.getDisplayMode()
        mainViewModel.setDisplayMode(MainViewModel.DisplayMode.SIDE_BY_SIDE)
        val secondDisplayMode = mainViewModel.getDisplayMode()

        assertThat(firstDisplayMode).isEqualTo(MainViewModel.DisplayMode.STANDARD)
        assertThat(secondDisplayMode).isEqualTo(MainViewModel.DisplayMode.SIDE_BY_SIDE)
    }
}