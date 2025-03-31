package com.example.weatherapp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDataResponseHourly(
    @SerialName("city_name") val cityName: String,
    @SerialName("country_code") val countryCode: String,
    val data: List<HourlyWeather>, // Lista de previsões horárias
    val lat: Float,
    val lon: Float,
    @SerialName("state_code") val stateCode: String,
    val timezone: String
)

@Serializable
data class HourlyWeather(
    @SerialName("timestamp_local") val timestampLocal: String, // Timestamp local
    @SerialName("timestamp_utc") val timestampUtc: String, // Timestamp UTC
    val temp: Float, // Temperatura atual
    @SerialName("app_temp") val appTemp: Float, // Sensação térmica
    val pop: Int, // Probabilidade de precipitação (%)
    val precip: Float, // Precipitação (mm)
    val rh: Int, // Umidade relativa (%)
    val clouds: Int, // Cobertura de nuvens (%)
    @SerialName("clouds_hi") val cloudsHi: Int, // Cobertura de nuvens (alto)
    @SerialName("clouds_low") val cloudsLow: Int, // Cobertura de nuvens (baixo)
    @SerialName("clouds_mid") val cloudsMid: Int, // Cobertura de nuvens (médio)
    val datetime: String, // Data e hora
    val dewpt: Float, // Ponto de orvalho
    val dhi: Float, // Irradiação direta horizontal
    val dni: Float, // Irradiação direta normal
    val ghi: Float, // Irradiação global horizontal
    val ozone: Int, // Nível de ozônio
    val pod: String, // Parâmetro de parte do dia (pode ser 'd' ou 'n')
    val pres: Int, // Pressão
    val slp: Int, // Pressão ao nível do mar
    val snow: Float, // Quantidade de neve
    @SerialName("snow_depth") val snowDepth: Float, // Profundidade da neve
    @SerialName("solar_rad") val solarRad: Float, // Radiação solar
    val temp2: Float? = null, // Tornando 'temp2' opcional
    val ts: Long, // Timestamp
    val uv: Int, // Índice UV
    val vis: Float, // Visibilidade
    val weather: WeatherDescription, // Descrição do clima
    @SerialName("wind_cdir") val windCdir: String, // Direção do vento
    @SerialName("wind_cdir_full") val windCdirFull: String, // Direção completa do vento
    @SerialName("wind_dir") val windDir: Int, // Direção do vento (em graus)
    @SerialName("wind_gust_spd") val windGustSpd: Float, // Velocidade do vento em rajada
    @SerialName("wind_spd") val windSpd: Float // Velocidade do vento
)



