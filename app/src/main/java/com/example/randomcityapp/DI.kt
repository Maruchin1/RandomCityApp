package com.example.randomcityapp

import com.example.randomcityapp.core.*
import com.example.randomcityapp.data.LocalRandomCityRepo
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

    single<RandomCityRepo> { LocalRandomCityRepo() }
}

val coreModule = module {

    single { SystemUtil() }

    single { RandomCityGenerator(get()) }

    single { RandomCityProducer(get(), get()) }

    viewModel { MainViewModel(get()) }
}