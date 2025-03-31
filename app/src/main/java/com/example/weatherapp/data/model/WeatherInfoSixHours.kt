package com.example.weatherapp.data.model

data class WeatherInfoSixHours(
    val hours: List<DayWeatherInfoMin>
)


data class DayWeatherInfoMin(
    val hour: String,
    val maxTemp: Float,
    val icon: String,
)