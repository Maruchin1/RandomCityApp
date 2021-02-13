package com.example.randomcityapp.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.example.randomcityapp.core.CityLocationApi
import com.google.android.gms.maps.model.LatLng
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*


class KtorCityLocationApi(
    private val context: Context
) : CityLocationApi {

    private val client: HttpClient by lazy {
        HttpClient(CIO) { install(JsonFeature) }
    }

    private val apiKey: String by lazy {
        val app: ApplicationInfo = context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        val bundle = app.metaData
        bundle.getString("com.google.android.geo.API_KEY", "")
    }

    override suspend fun getCityLocation(cityName: String): LatLng? {
        val response: PlaceSearchResponse = client.get(buildPlaceSearchUrl(cityName))
        if (response.noSearchResults) return null
        val location = response.firstFoundLocation
        return LatLng(location.lat, location.lng)
    }

    private fun buildPlaceSearchUrl(cityName: String): String {
        val baseUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json"
        val paramApiKey = "key=$apiKey"
        val paramInput = "input=$cityName"
        val paramInputType = "inputtype=textquery"
        val paramFields = "fields=geometry"
        return "$baseUrl?$paramApiKey&$paramInput&$paramInputType&$paramFields"
    }

}