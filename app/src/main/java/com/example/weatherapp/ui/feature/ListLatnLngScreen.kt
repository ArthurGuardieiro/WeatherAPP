package com.example.weatherapp.ui.feature

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.data.local.LatLng
import com.example.weatherapp.ui.UiEvent
import com.example.weatherapp.ui.theme.WeatherAPPTheme

@Composable
fun ListLatLngRoute(
    viewModel: ListViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel(),
    navigateBack: () -> Unit,
) {
    val latLngs by viewModel.latLngs.collectAsState()

    val uiEvent = viewModel.uiEvent.collectAsState(initial = null)

    ListLatLngScreen(
        navigateToAddLatLngScreen = { navigateBack() },
        latLngs = latLngs,
        viewModel = viewModel,
        navigateBack = navigateBack,
        weatherViewModel = weatherViewModel
    )
}

@Composable
fun LatLtngItem(
    latLng: LatLng,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onItemClick() }
            ) {
                Text(
                    text = latLng.cityName,
                    style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Lat: ${latLng.lat} Lon: ${latLng.lng}",
                    style = MaterialTheme.typography.bodyLarge)
            }

            IconButton(onClick = onDeleteClick ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
            }
        }
    }
}

@Composable
fun ListLatLngScreen(
    navigateToAddLatLngScreen: (id: Long?) -> Unit,
    latLngs: List<LatLng>,
    viewModel: ListViewModel,
    navigateBack: () -> Unit,
    weatherViewModel: WeatherViewModel
) {
    val uiEvent by viewModel.uiEvent.collectAsState(initial = null)

    // Observa os eventos e executa a navegação
    LaunchedEffect(uiEvent) {
        when(uiEvent){
            is UiEvent.Navigate<*> -> {}
            UiEvent.NavigateBack -> {
                navigateBack()
            }
            is UiEvent.ShowSnackbar -> {}
            null -> {}
            is UiEvent.UpdateWeather -> {
                weatherViewModel.updateLocation((uiEvent as UiEvent.UpdateWeather).lat, (uiEvent as UiEvent.UpdateWeather).lng)
                navigateBack()
            }
        }
    }

    ListLatLngContent(
        latLngs = latLngs,
        onAddItemClick = navigateToAddLatLngScreen,
        onEvent = { event -> viewModel.onEvent(event) }
    )
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListLatLngContent(
    latLngs: List<LatLng>,
    onAddItemClick: (id: Long?) -> Unit,
    onEvent: (ListEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddItemClick(null) }) {
                Icon(Icons.Default.Add, contentDescription = "Create")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(it),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(latLngs) { ll ->
                LatLtngItem(
                    LatLng(ll.id, ll.lat, ll.lng, ll.cityName),
                    onItemClick = { onEvent(ListEvent.Selected(ll.id)) },
                    onDeleteClick = { onEvent(ListEvent.Delete(ll.id)) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ListLatLngContentPreview() {
    WeatherAPPTheme {
        ListLatLngContent(
            listOf(LatLng(1, 10f, 10f, "udi")),
            onAddItemClick = {},
            onEvent = {}
        )
    }

}
