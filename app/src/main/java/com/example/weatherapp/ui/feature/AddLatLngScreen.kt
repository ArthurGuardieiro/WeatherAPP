package com.example.weatherapp.ui.feature

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.UiEvent
import com.example.weatherapp.ui.theme.WeatherAPPTheme

@Composable
fun AddLatLngRoute(
    viewModel: AddLatLngViewModel = viewModel(),
    navigateBack: () -> Unit
) {
    val lat = viewModel.lat
    val lng = viewModel.lng


    AddLatLngScreen(
        lat = lat,
        lng = lng,
        onLatChange = { newLat ->
            // Simplesmente passamos a string para o ViewModel sem tentativas de conversão
            viewModel.onEvent(AddLatLngEvent.latChanged(newLat))
        },
        onLngChange = { newLng ->
            // Simplesmente passamos a string para o ViewModel sem tentativas de conversão
            viewModel.onEvent(AddLatLngEvent.lngChanged(newLng))
        },
        onSave = { viewModel.onEvent(AddLatLngEvent.Save) },
        viewModel,
        navigateBack
    )
}

@Composable
fun AddLatLngScreen(
    lat: String,
    lng: String,
    onLatChange: (String) -> Unit,
    onLngChange: (String) -> Unit,
    onSave: () -> Unit,
    viewModel: AddLatLngViewModel,
    navigateBack: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent){
                is UiEvent.ShowSnackbar -> {

                }
                UiEvent.NavigateBack -> {
                    navigateBack()
                }
                is UiEvent.Navigate<*> -> {

                }

                is UiEvent.UpdateWeather -> {

                }
            }
        }
    }

    AddLatLngContent(
        lat = lat,
        lng = lng,
        onLatChange = onLatChange,
        onLngChange = onLngChange,
        onSave = onSave
    )
}

@Composable
fun AddLatLngContent(
    lat: String,
    lng: String,
    onLatChange: (String) -> Unit,
    onLngChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onSave) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = lat,
                onValueChange = onLatChange,
                placeholder = { Text(text = "Latitude") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = lng,
                onValueChange = onLngChange,
                placeholder = { Text(text = "Longitude") }
            )
        }
    }
}

@Preview
@Composable
private fun AddLatLngContentPreview() {
    WeatherAPPTheme {
        AddLatLngContent(
            lat = "",
            lng = "",
            onLatChange = {},
            onLngChange = {},
            onSave = {}
        )
    }
}