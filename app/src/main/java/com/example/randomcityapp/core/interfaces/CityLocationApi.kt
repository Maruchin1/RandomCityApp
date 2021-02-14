package com.example.randomcityapp.core.interfaces

import com.google.android.gms.maps.model.LatLng

interface CityLocationApi {

    suspend fun getCityLocation(cityName: String): LatLng?
}