package com.example.weatherapp.ui

sealed  interface UiEvent {
    data class ShowSnackbar(val message: String): UiEvent
    data object NavigateBack: UiEvent
    data class Navigate<T : Any>(val route: T) : UiEvent
    data class UpdateWeather(val lat: Float, val lng: Float) : UiEvent
}