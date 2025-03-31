package com.example.weatherapp.ui.feature

import com.example.weatherapp.data.model.WeatherInfoSixHours

data class WeatherInfoSixHoursState(
    val weatherInfoSixHours: WeatherInfoSixHours? = null,
    val errorMessage: String? = null
)