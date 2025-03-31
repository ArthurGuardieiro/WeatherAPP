package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.model.DayWeatherInfo
import com.example.weatherapp.data.model.DayWeatherInfoMin
import com.example.weatherapp.data.model.FutureModel
import com.example.weatherapp.data.model.WeatherInfo
import com.example.weatherapp.data.model.WeatherInfoSevenDays
import com.example.weatherapp.data.model.WeatherInfoSixHours
import com.example.weatherapp.data.remote.RemoteDataSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Float, lng: Float): WeatherInfo {
        val response = remoteDataSource.getWeatherDataResponse(lat, lng)
        val weather = response.weather[0]

        return WeatherInfo(
            locationName = response.name,
            conditionIcon = weather.icon,
            condition = weather.main,
            temperature = response.main.temp.roundToInt(),
            dayOfWeek = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
            isDay = weather.icon.last() == 'd',
            humidity = response.main.humidity,
            windSpeed = response.wind.speed,
            rain = response.rain?.oneHour ?: 0.0
        )
    }

    override suspend fun getWeatherDataSevenDays(lat: Float, lng: Float): WeatherInfoSevenDays {

        val response = remoteDataSource.getWeatherDataResponseSevenDays(lat, lng)
        val weather = response.data

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val locale = Locale("pt", "BR")  // Definindo o idioma como português do Brasil
        val dayFormatter = DateTimeFormatter.ofPattern("EEE", locale) // Formato abreviado do dia da semana

        val weatherbitToOpenWeatherIconMap = mapOf(
            // Céu limpo
            "c01d" to "01d", // Céu limpo durante o dia
            "c01n" to "01n", // Céu limpo durante a noite

            // Poucas nuvens
            "c02d" to "02d", // Poucas nuvens durante o dia
            "c02n" to "02n", // Poucas nuvens durante a noite

            // Nuvens dispersas
            "c03d" to "03d", // Nuvens dispersas durante o dia
            "c03n" to "03n", // Nuvens dispersas durante a noite

            // Nuvens quebradas
            "c04d" to "04d", // Nuvens quebradas durante o dia
            "c04n" to "04n", // Nuvens quebradas durante a noite

            // Chuva leve
            "r01d" to "09d", // Chuva leve durante o dia
            "r01n" to "09n", // Chuva leve durante a noite

            // Chuva
            "r02d" to "10d", // Chuva durante o dia
            "r02n" to "10n", // Chuva durante a noite
            "r03d" to "11d",
            "ro3n" to "11n",

            // Tempestade
            "t01d" to "11d", // Tempestade durante o dia
            "t01n" to "11n", // Tempestade durante a noite

            // Neve
            "s01d" to "13d", // Neve durante o dia
            "s01n" to "13n", // Neve durante a noite

            // Névoa/Neblina
            "a01d" to "50d", // Névoa durante o dia
            "a01n" to "50n", // Névoa durante a noite
        )

        val days = weather.drop(1).take(7).map { dailyWeather ->
            // Convertendo a string datetime para LocalDate
            val date = LocalDate.parse(dailyWeather.datetime, formatter)
            val dayOfWeek = date.format(dayFormatter) // Formata para o dia da semana abreviado (ex: "dom", "seg")

            // Convertendo o ícone do Weatherbit para o ícone do OpenWeather
            val openWeatherIcon = weatherbitToOpenWeatherIconMap[dailyWeather.weather.icon] ?: "01d"  // Fallback para "01d" se não encontrado

            DayWeatherInfo(
                day = dayOfWeek, // Nome do dia da semana abreviado em português
                maxTemp = dailyWeather.maxTemp, // MaxTemp
                minTemp = dailyWeather.minTemp, // MinTemp
                icon = openWeatherIcon, // Ícone convertido
                description = dailyWeather.weather.description, // Descrição
            )
        }

        return WeatherInfoSevenDays(
            days = days // Passando a lista de dias com todas as informações
        )
    }

    override suspend fun getWeatherDataSixHours(lat: Float, lng: Float): WeatherInfoSixHours {

        val weatherbitToOpenWeatherIconMap = mapOf(
            // Céu limpo
            "c01d" to "01d", // Céu limpo durante o dia
            "c01n" to "01n", // Céu limpo durante a noite

            // Poucas nuvens
            "c02d" to "02d", // Poucas nuvens durante o dia
            "c02n" to "02n", // Poucas nuvens durante a noite

            // Nuvens dispersas
            "c03d" to "03d", // Nuvens dispersas durante o dia
            "c03n" to "03n", // Nuvens dispersas durante a noite

            // Nuvens quebradas
            "c04d" to "04d", // Nuvens quebradas durante o dia
            "c04n" to "04n", // Nuvens quebradas durante a noite

            // Chuva leve
            "r01d" to "09d", // Chuva leve durante o dia
            "r01n" to "09n", // Chuva leve durante a noite

            // Chuva
            "r02d" to "10d", // Chuva durante o dia
            "r02n" to "10n", // Chuva durante a noite
            "r03d" to "11d",
            "ro3n" to "11n",

            // Tempestade
            "t01d" to "11d", // Tempestade durante o dia
            "t01n" to "11n", // Tempestade durante a noite

            // Neve
            "s01d" to "13d", // Neve durante o dia
            "s01n" to "13n", // Neve durante a noite

            // Névoa/Neblina
            "a01d" to "50d", // Névoa durante o dia
            "a01n" to "50n", // Névoa durante a noite
        )

        return try {
            val response = remoteDataSource.getWeatherDataResponseSixHours(lat, lng)
            Log.d("function", "Response recebido com sucesso: $response")

            val weather = response.data
            Log.d("function", "Dados da previsão: $weather")

            val hours = weather.take(6).map { hourWeather ->
                val openWeatherIcon = weatherbitToOpenWeatherIconMap[hourWeather.weather.icon] ?: "01d"  // Fallback para "01d" se não encontrado
                DayWeatherInfoMin(
                    hour = formatTimeToHHMM(hourWeather.timestampLocal),
                    maxTemp = hourWeather.temp,
                    icon = openWeatherIcon
                )
            }

            WeatherInfoSixHours(hours = hours)
        } catch (e: Exception) {
            Log.e("function", "Erro ao obter os dados da previsão", e)
            throw e
        }
    }

    fun formatTimeToHHMM(dateTimeString: String): String {
        // Parse a string para LocalDateTime
        val dateTime = LocalDateTime.parse(dateTimeString)

        // Cria um formatter para o formato "hh:mm"
        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        // Retorna a string formatada
        return dateTime.format(formatter)
    }



}