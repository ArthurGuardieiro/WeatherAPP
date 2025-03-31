package com.example.weatherapp.ui.feature

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.repository.LatLngRepository
import com.example.weatherapp.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLatLngViewModel @Inject constructor(
    private val repository: LatLngRepository
) : ViewModel() {

    var lat by mutableStateOf("")
        private set

    var lng by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddLatLngEvent) {
        when (event) {
            is AddLatLngEvent.latChanged -> {
                lat = event.latString // Agora recebemos a string diretamente
            }
            is AddLatLngEvent.lngChanged -> {
                lng = event.lngString // Agora recebemos a string diretamente
            }
            AddLatLngEvent.Save -> {
                save()
            }
        }
    }

    private fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            // Converte as strings para Float, se possível
            val latValue = lat.toFloatOrNull()
            val lngValue = lng.toFloatOrNull()

            // Verifica se a conversão foi bem-sucedida
            if (latValue == null || lngValue == null) {
                _uiEvent.send(UiEvent.ShowSnackbar("Tipo de dado errado"))
                return@launch
            }

            // Agora os valores são válidos, vamos salvar no repositório
            try {
                repository.insert(latValue, lngValue)
                _uiEvent.send(UiEvent.NavigateBack)
            } catch (e: Exception) {
                Log.d("error_insert", "erro no insert")
            }
        }
    }
}


