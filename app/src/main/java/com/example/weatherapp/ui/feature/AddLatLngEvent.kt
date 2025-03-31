package com.example.weatherapp.ui.feature


sealed class AddLatLngEvent {
    data class latChanged(val latString: String) : AddLatLngEvent()
    data class lngChanged(val lngString: String) : AddLatLngEvent()
    object Save : AddLatLngEvent()
}