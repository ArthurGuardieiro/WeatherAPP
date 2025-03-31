package com.example.weatherapp.data

import com.example.weatherapp.data.remote.RemoteDataSource
import com.example.weatherapp.data.remote.response.WeatherDataResponse
import com.example.weatherapp.data.remote.response.WeatherDataResponseHourly
import com.example.weatherapp.data.remote.response.WeatherDataResponseSevenDays
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class KtorRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) : RemoteDataSource{
    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5"
    }

    override suspend fun getWeatherDataResponse(lat: Float, lng: Float): WeatherDataResponse {
        return httpClient
            .get("${BASE_URL}/weather?lat=$lat&lon=$lng&appid=d9ee3f09b79a8fe2f11db51b0eadc4ca&units=metric")
            .body()
    }

    override suspend fun getWeatherDataResponseSevenDays(lat: Float, lng: Float): WeatherDataResponseSevenDays {
        return httpClient
            .get("https://api.weatherbit.io/v2.0/forecast/daily?lat=${lat}&lon=${lng}&key=876339f534b247be81f921602a24008c&days=8&lang=pt")
            .body()
    }

    override suspend fun getWeatherDataResponseSixHours(lat: Float, lng: Float): WeatherDataResponseHourly{
        return httpClient
            .get("https://api.weatherbit.io/v2.0/forecast/hourly?lat=${lat}&lon=${lng}&hours=7&key=876339f534b247be81f921602a24008c&days=8&lang=pt")
            .body()
    }
}