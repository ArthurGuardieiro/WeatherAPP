package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.weatherapp.ui.feature.AddLatLngEvent
import com.example.weatherapp.ui.feature.AddLatLngScreen
import com.example.weatherapp.ui.feature.AddLatLngViewModel
import com.example.weatherapp.ui.feature.ListLatLngScreen
import com.example.weatherapp.ui.feature.ListViewModel
import com.example.weatherapp.ui.feature.WeatherScreen
import com.example.weatherapp.ui.feature.WeatherViewModel
import kotlinx.serialization.Serializable

@Serializable
object LatLngRoute

@Serializable
data class ListLatLngRoute(val id: Long? = null)

@Serializable
data class AddLatLngRoute(val id: Long? = null)

@Composable
fun TodoNavHost(
    viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    viewModelAdd: AddLatLngViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    viewModelList: ListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val weatherInfoState by viewModel.weatherInfoState.collectAsStateWithLifecycle()
    val weatherInfoSevenDaysState by viewModel.weatherInfoSevenDaysState.collectAsStateWithLifecycle()
    val weatherInfoSixHoursState by viewModel.weatherInfoSixHoursState.collectAsStateWithLifecycle()

    val lat = viewModelAdd.lat
    val lng = viewModelAdd.lng

    val latLngs by viewModelList.latLngs.collectAsState()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LatLngRoute) {
        composable<LatLngRoute> {
            WeatherScreen(weatherInfo = weatherInfoState.weatherInfo
                ,weatherInfoSevenDays = weatherInfoSevenDaysState.weatherInfoSevenDays,
                weatherInfoSixHours = weatherInfoSixHoursState.weatherInfoSixHours,
                onAddItemClick = { id ->
                    navController.navigate(ListLatLngRoute(id = id))
                })
        }

        composable<ListLatLngRoute> { backStackEntry ->
            val listLatLngRoute = backStackEntry.toRoute<ListLatLngRoute>()
            ListLatLngScreen(
                navigateToAddLatLngScreen = { id ->
                    navController.navigate(AddLatLngRoute(id = id))
                },
                latLngs = latLngs,
                viewModel = viewModelList,
                navigateBack = { navController.popBackStack() },
                weatherViewModel = viewModel
            )
        }

        composable<AddLatLngRoute> { backStackEntry ->
            val addLatLngRoute = backStackEntry.toRoute<AddLatLngRoute>()
            AddLatLngScreen(
                lat = lat,
                lng = lng,
                onLatChange = { viewModelAdd.onEvent(AddLatLngEvent.latChanged(it)) },
                onLngChange = { viewModelAdd.onEvent(AddLatLngEvent.lngChanged(it)) },
                onSave = { viewModelAdd.onEvent(AddLatLngEvent.Save) },
                viewModel = viewModelAdd,
                navigateBack = { navController.popBackStack() }
            )
        }

    }

}