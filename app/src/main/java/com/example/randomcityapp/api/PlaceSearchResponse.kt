package com.example.randomcityapp.api

data class PlaceSearchResponse(val status: String, val candidates: List<PlaceCandidate>) {

    val noSearchResults: Boolean
        get() = candidates.isEmpty()

    val firstFoundLocation: PlaceLocation
        get() = candidates.first().geometry.location
}