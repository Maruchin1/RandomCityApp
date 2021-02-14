package com.example.randomcityapp.api

import com.example.randomcityapp.core.interfaces.CityLocationApi
import com.google.android.gms.maps.model.LatLng
import io.ktor.client.*
import io.ktor.client.request.*


class KtorCityLocationApi(
    private val httpClient: HttpClient,
    private val apiKey: String
) : CityLocationApi {

    override suspend fun getCityLocation(cityName: String): LatLng? {
        val response: PlaceSearchResponse = httpClient.get(buildPlaceSearchUrl(cityName))
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