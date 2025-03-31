package com.example.weatherapp.ui.feature

sealed interface ListEvent {
    data class Delete(val id: Long) : ListEvent
    data class Selected(val id: Long): ListEvent
}