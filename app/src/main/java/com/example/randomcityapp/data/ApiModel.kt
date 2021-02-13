package com.example.randomcityapp.data

data class PlaceSearchResponse(val status: String, val candidates: List<PlaceCandidate>) {

    val noSearchResults: Boolean
        get() = candidates.isEmpty()

    val firstFoundLocation: PlaceLocation
        get() = candidates.first().geometry.location
}

data class PlaceCandidate(val geometry: PlaceGeometry)

data class PlaceGeometry(val location: PlaceLocation)

data class PlaceLocation(val lat: Double, val lng: Double)