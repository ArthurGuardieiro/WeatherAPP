package com.example.weatherapp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDataResponseSevenDays(
    @SerialName("city_name") val cityName: String,
    @SerialName("country_code") val countryCode: String,
    val data: List<DailyWeather>, // Dados diários de previsão
    val lat: Float,
    val lon: Float,
    @SerialName("state_code") val stateCode: String,
    val timezone: String
)

@Serializable
data class DailyWeather(
    @SerialName("app_max_temp") val maxTemp: Float,
    @SerialName("app_min_temp") val minTemp: Float,
    val clouds: Int,
    @SerialName("clouds_hi") val cloudsHi: Int,
    @SerialName("clouds_low") val cloudsLow: Int,
    @SerialName("clouds_mid") val cloudsMid: Int,
    val datetime: String,
    @SerialName("dewpt") val dewPoint: Float,
    @SerialName("high_temp") val highTemp: Float,
    @SerialName("low_temp") val lowTemp: Float,
    @SerialName("max_dhi") val maxDhi: Float?,
    @SerialName("max_temp") val maxTemp2: Float,
    @SerialName("min_temp") val minTemp2: Float,
    @SerialName("moon_phase") val moonPhase: Float,
    @SerialName("moon_phase_lunation") val moonPhaseLunation: Float,
    @SerialName("moonrise_ts") val moonriseTs: Long,
    @SerialName("moonset_ts") val moonsetTs: Long,
    val ozone: Int,
    val pop: Int,
    val precip: Float,
    val pres: Int,
    val rh: Int, // Umidade relativa
    val slp: Int,
    val snow: Float,
    @SerialName("snow_depth") val snowDepth: Float,
    @SerialName("sunrise_ts") val sunriseTs: Long,
    @SerialName("sunset_ts") val sunsetTs: Long,
    val temp: Float, // Temperatura atual
    val ts: Long,
    val uv: Int,
    @SerialName("valid_date") val validDate: String,
    val vis: Float, // Visibilidade
    val weather: WeatherDescription,
    @SerialName("wind_cdir") val windCdir: String,
    @SerialName("wind_cdir_full") val windCdirFull: String,
    @SerialName("wind_dir") val windDir: Int,
    @SerialName("wind_gust_spd") val windGustSpd: Float,
    @SerialName("wind_spd") val windSpd: Float
)

@Serializable
data class WeatherDescription(
    val icon: String,
    val description: String,
    val code: Int
)
