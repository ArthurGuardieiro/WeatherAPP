package com.example.weatherapp.data.remote

import com.example.weatherapp.data.remote.response.WeatherDataResponse
import com.example.weatherapp.data.remote.response.WeatherDataResponseHourly
import com.example.weatherapp.data.remote.response.WeatherDataResponseSevenDays

interface RemoteDataSource {

    suspend fun getWeatherDataResponse(lat: Float, lng: Float): WeatherDataResponse

    suspend fun getWeatherDataResponseSevenDays(lat: Float, lng: Float): WeatherDataResponseSevenDays

    suspend fun getWeatherDataResponseSixHours(lat: Float, lng: Float): WeatherDataResponseHourly

}