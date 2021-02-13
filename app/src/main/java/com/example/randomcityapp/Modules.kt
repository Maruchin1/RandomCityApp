package com.example.randomcityapp

import com.example.randomcityapp.core.MainViewModel
import com.example.randomcityapp.core.RandomCityRepo
import com.example.randomcityapp.data.LocalRandomCityRepo
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

    single<RandomCityRepo> { LocalRandomCityRepo() }
}

val coreModule = module {

    viewModel { MainViewModel(randomCityRepo = get()) }
}