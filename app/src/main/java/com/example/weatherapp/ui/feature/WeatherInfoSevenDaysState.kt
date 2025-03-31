package com.example.weatherapp.ui.feature

import com.example.weatherapp.data.model.WeatherInfoSevenDays

data class WeatherInfoSevenDaysState(
    val weatherInfoSevenDays: WeatherInfoSevenDays? = null,
    val errorMessage: String? = null
)