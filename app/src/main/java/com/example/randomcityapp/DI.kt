package com.example.randomcityapp

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.room.Room
import com.example.randomcityapp.core.interfaces.CityLocationApi
import com.example.randomcityapp.core.interfaces.RandomCityRepo
import com.example.randomcityapp.core.logic.RandomCityGenerator
import com.example.randomcityapp.core.logic.RandomCityProducer
import com.example.randomcityapp.core.logic.SystemUtil
import com.example.randomcityapp.core.view_models.MainViewModel
import com.example.randomcityapp.api.KtorCityLocationApi
import com.example.randomcityapp.database.AppDatabase
import com.example.randomcityapp.database.RoomRandomCityRepo
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NAME_API_KEY = "API_KEY"
const val METADATA_API_KEY = "com.google.android.geo.API_KEY"
const val DATABASE_NAME = "random_city_database"

val apiModule = module {

    single { HttpClient(CIO) { install(JsonFeature) } }

    single(named(NAME_API_KEY)) {
        val app: ApplicationInfo = androidContext().packageManager
            .getApplicationInfo(androidContext().packageName, PackageManager.GET_META_DATA)
        val bundle = app.metaData
        bundle.getString(METADATA_API_KEY, "")
    }

    single<CityLocationApi> { KtorCityLocationApi(get(), get(named(NAME_API_KEY))) }
}

val databaseModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DATABASE_NAME).build()
    }

    factory { get<AppDatabase>().randomCityDao() }

    single<RandomCityRepo> { RoomRandomCityRepo(get()) }
}

val coreModule = module {

    single { SystemUtil() }

    single { RandomCityGenerator(get()) }

    single { RandomCityProducer(get(), get()) }

    viewModel { MainViewModel(get(), get()) }
}