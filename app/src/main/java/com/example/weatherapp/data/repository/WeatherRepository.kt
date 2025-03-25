package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.WeatherInfo

interface WeatherRepository {

    suspend fun getWeatherData(lat: Float, lng: Float): WeatherInfo

}