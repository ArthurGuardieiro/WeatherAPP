package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.LatLngDao
import com.example.weatherapp.data.local.LatLngEntity
import javax.inject.Inject

interface LatLngRepository  {

    fun getAllUsers(): List<LatLngEntity>
    fun insertUser(LatLng: LatLngEntity)

}