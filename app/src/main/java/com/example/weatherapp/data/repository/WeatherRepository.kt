package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.FutureModel
import com.example.weatherapp.data.model.WeatherInfo
import com.example.weatherapp.data.model.WeatherInfoSevenDays
import com.example.weatherapp.data.model.WeatherInfoSixHours

interface WeatherRepository {

    suspend fun getWeatherData(lat: Float, lng: Float): WeatherInfo

    suspend fun getWeatherDataSevenDays(lat: Float, lng: Float): WeatherInfoSevenDays

    suspend fun getWeatherDataSixHours(lat: Float, lng: Float): WeatherInfoSixHours

}