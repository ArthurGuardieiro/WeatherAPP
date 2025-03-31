package com.example.weatherapp.data.model

data class WeatherInfo(
    val locationName: String,
    val conditionIcon: String,
    val condition: String,
    val temperature: Int,
    val dayOfWeek: String,
    val isDay: Boolean,
    val humidity: Int,
    val windSpeed: Double,
    val rain: Double
)