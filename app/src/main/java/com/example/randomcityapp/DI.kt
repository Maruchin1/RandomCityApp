package com.example.randomcityapp

import com.example.randomcityapp.core.interfaces.CityLocationApi
import com.example.randomcityapp.core.interfaces.RandomCityRepo
import com.example.randomcityapp.core.logic.RandomCityGenerator
import com.example.randomcityapp.core.logic.RandomCityProducer
import com.example.randomcityapp.core.logic.SystemUtil
import com.example.randomcityapp.core.view_models.MainViewModel
import com.example.randomcityapp.data.KtorCityLocationApi
import com.example.randomcityapp.data.LocalRandomCityRepo
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {

    single<RandomCityRepo> { LocalRandomCityRepo() }

    single<CityLocationApi> { KtorCityLocationApi(get()) }
}

val coreModule = module {

    single { SystemUtil() }

    single { RandomCityGenerator(get()) }

    single { RandomCityProducer(get(), get()) }

    viewModel { MainViewModel(get(), get()) }
}