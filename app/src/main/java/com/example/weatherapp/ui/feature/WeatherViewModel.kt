package com.example.weatherapp.ui.feature

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.remote.di.LocationHelper
import com.example.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    application: Application,
) : ViewModel() {

    private val locationHelper = LocationHelper(application.applicationContext)
    private val _weatherInfoState = MutableStateFlow(WeatherInfoState())
    val weatherInfoState: StateFlow<WeatherInfoState> = _weatherInfoState.asStateFlow()



    init {
        getWeatherInfo()
    }

    private fun getWeatherInfo() {
        locationHelper.getCurrentLocation { location ->
            location?.let {
                val details = locationHelper.getLocationDetails(it)
                val lat = details["latitude"] as? Double ?: return@getCurrentLocation
                val lng = details["longitude"] as? Double ?: return@getCurrentLocation

                println("Latitude: $lat")
                println("Longitude: $lng")

                viewModelScope.launch {
                    try {
                        val weatherInfo = weatherRepository.getWeatherData(lat.toFloat(), lng.toFloat())
                        _weatherInfoState.update {
                            it.copy(weatherInfo = weatherInfo)
                        }
                    } catch (e: Exception) {
                        _weatherInfoState.update {
                            it.copy(errorMessage = "Erro ao buscar dados meteorológicos.")
                        }
                    }
                }
            } ?: run {
                _weatherInfoState.update {
                    it.copy(errorMessage = "Não foi possível obter a localização.")
                }
            }
        }
    }

}