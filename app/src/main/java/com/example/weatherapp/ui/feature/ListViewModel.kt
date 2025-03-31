package com.example.weatherapp.ui.feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.LatLng
import com.example.weatherapp.data.repository.LatLngRepository
import com.example.weatherapp.navigation.LatLngRoute
import com.example.weatherapp.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: LatLngRepository,
) : ViewModel() {

    private val _latLngs = MutableStateFlow<List<LatLng>>(emptyList())
    val latLngs: StateFlow<List<LatLng>> = _latLngs.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ListEvent) {
        when(event){
            is ListEvent.Delete -> {
                delete(event.id)
            }
            is ListEvent.Selected -> {
                viewModelScope.launch {
                    val latLng = repository.getBy(event.id)
                    latLng?.let {
                        Log.d("aqui", "Entrou")
                        _uiEvent.send(UiEvent.UpdateWeather(latLng.lat, latLng.lng))
                    }
                }
            }
        }
    }



    private fun delete(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
            _latLngs.value = repository.getAllUsers()
                .stateIn(viewModelScope).value // Atualiza imediatamente a lista
        }
    }

    init {
        viewModelScope.launch {
            repository.getAllUsers().collect { latLngList ->
                _latLngs.value = latLngList
            }
        }
    }
}

