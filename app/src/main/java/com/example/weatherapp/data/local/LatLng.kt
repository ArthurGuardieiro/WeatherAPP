package com.example.weatherapp.data.local

import kotlinx.serialization.Serializable

@Serializable
data class LatLng(
    val id: Long,
    val lat: Float,
    val lng: Float,
    val cityName: String
) {

}
