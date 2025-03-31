package com.example.weatherapp.data.model

import com.example.weatherapp.data.remote.response.DailyWeather
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class WeatherInfoSevenDays(
    val days: List<DayWeatherInfo>
)


data class DayWeatherInfo(
    val day: String,
    val maxTemp: Float,
    val minTemp: Float,
    val icon: String,
    val description: String,
)



