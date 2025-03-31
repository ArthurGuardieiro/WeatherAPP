package com.example.weatherapp.ui.feature

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.LatLngEntity
import com.example.weatherapp.data.model.WeatherInfoSevenDays
import com.example.weatherapp.data.model.WeatherInfoSixHours
import com.example.weatherapp.data.remote.di.LocationHelper
import com.example.weatherapp.data.repository.LatLngRepository
import com.example.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val latLngRepository: LatLngRepository,
    application: Application,
) : ViewModel() {

    private val locationHelper = LocationHelper(application.applicationContext)
    private val _weatherInfoState = MutableStateFlow(WeatherInfoState())
    val weatherInfoState: StateFlow<WeatherInfoState> = _weatherInfoState.asStateFlow()

    private val _weatherInfoSevenDays = MutableStateFlow(WeatherInfoSevenDaysState())
    val weatherInfoSevenDaysState: StateFlow<WeatherInfoSevenDaysState> = _weatherInfoSevenDays.asStateFlow()

    private val _weatherInfoSixHours = MutableStateFlow(WeatherInfoSixHoursState())
    val weatherInfoSixHoursState: StateFlow<WeatherInfoSixHoursState> = _weatherInfoSixHours.asStateFlow()


    init {
        getWeatherInfo()
    }

    private fun getWeatherInfo() {
        locationHelper.getCurrentLocation { location ->
            location?.let {
                val details = locationHelper.getLocationDetails(it)
                val lat = details["latitude"] as? Double ?: return@getCurrentLocation
                val lng = details["longitude"] as? Double ?: return@getCurrentLocation

                Log.d("test","Latitude: $lat")
                Log.d("test","Longitude: $lng")


                viewModelScope.launch(Dispatchers.IO) {
                    latLngRepository.insertUser(
                        LatLngEntity(1, 37.421998333333335f, -122.084f)
                    )
                    val result = latLngRepository.getAllUsers()
                    try {
                        val weatherInfo = weatherRepository.getWeatherData(lat.toFloat(), lng.toFloat())
                        _weatherInfoState.update {
                            it.copy(weatherInfo = weatherInfo)
                        }
                    } catch (e: Exception) {
                        _weatherInfoState.update {
                            it.copy(errorMessage = "Erro ao buscar dados meteorológicos.")
                        }
                        Log.d("error", e.message.toString())
                    }

                    try {
                        val weatherInfoSevenDays = weatherRepository.getWeatherDataSevenDays(lat.toFloat(), lng.toFloat())

                        _weatherInfoSevenDays.update {
                            it.copy(weatherInfoSevenDays = weatherInfoSevenDays)
                        }
                    } catch (e: Exception) {
                        _weatherInfoSevenDays.update {
                            it.copy(errorMessage = "Erro ao buscar os 7 dias")
                        }
                        Log.d("error 7", e.message.toString())
                    }

                    try {
                        val weatherInfoSixHours = weatherRepository.getWeatherDataSixHours(lat.toFloat(), lng.toFloat())
                        Log.d("try", "entrou no try")
                        _weatherInfoSixHours.update {
                            it.copy(weatherInfoSixHours = weatherInfoSixHours)
                        }
                    } catch (e: Exception) {
                        _weatherInfoSixHours.update {
                            it.copy(errorMessage = "Erro ao buscar as 6 horas")
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