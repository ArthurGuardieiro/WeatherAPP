package com.example.weatherapp.data.remote.di

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LocationHelper(private val context: Context) {

    private lateinit var locationManager: LocationManager

    fun getCurrentLocation(callback: (Location?) -> Unit) {

        // Log inicial para diagnóstico
        Log.d("LOCATION_DEBUG", "Iniciando obtenção de localização")

        // Verifica permissões de localização
        if (!checkLocationPermissions()) {
            Log.e("LOCATION_DEBUG", "Permissões de localização não concedidas")
            callback(null)
            return
        }

        // Verifica se serviço de localização está ativo
        if (!isLocationEnabled()) {
            Log.e("LOCATION_DEBUG", "Serviço de localização desativado")
            callback(null)
            return
        }

        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Tenta obter localização do provedor de rede
        val networkProvider = LocationManager.NETWORK_PROVIDER
        // Tenta obter localização do GPS
        val gpsProvider = LocationManager.GPS_PROVIDER

        try {
            // Primeiro, tenta obter a última localização conhecida
            val lastKnownLocationNetwork = locationManager.getLastKnownLocation(networkProvider)
            val lastKnownLocationGPS = locationManager.getLastKnownLocation(gpsProvider)

            // Prioriza a localização do GPS
            val lastLocation = lastKnownLocationGPS ?: lastKnownLocationNetwork

            if (lastLocation != null) {
                Log.d("LOCATION_DEBUG", "Localização obtida por último local conhecido")
                callback(lastLocation)
                return
            }

            // Se não tiver localização anterior, solicita atualizações
            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    Log.d("LOCATION_DEBUG", "Nova localização obtida")
                    callback(location)
                    // Remove listener após receber a localização
                    locationManager.removeUpdates(this)
                }

                override fun onProviderEnabled(provider: String) {
                    Log.d("LOCATION_DEBUG", "Provedor habilitado: $provider")
                }

                override fun onProviderDisabled(provider: String) {
                    Log.e("LOCATION_DEBUG", "Provedor desabilitado: $provider")
                }
            }

            // Solicita atualizações de localização
            locationManager.requestLocationUpdates(
                gpsProvider, // Provedor preferido
                1000L,       // Tempo mínimo entre atualizações (milissegundos)
                10f,         // Distância mínima entre atualizações (metros)
                locationListener
            )

        } catch (e: SecurityException) {
            // Trata exceção de permissão
            Log.e("LOCATION_DEBUG", "Exceção de segurança ao obter localização", e)
            callback(null)
        }
    }

    // Verifica se as permissões de localização foram concedidas
    private fun checkLocationPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    // Verifica se o serviço de localização está ativo
    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    // Método para extrair informações de localização
    fun getLocationDetails(location: Location?): Map<String, Any?> {
        return mapOf(
            "latitude" to location?.latitude,
            "longitude" to location?.longitude,
            "precisao" to location?.accuracy,
            "altitude" to location?.altitude
        )
    }
}