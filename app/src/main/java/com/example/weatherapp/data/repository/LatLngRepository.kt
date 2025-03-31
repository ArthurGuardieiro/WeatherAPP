package com.example.weatherapp.data.repository

import androidx.room.Delete
import androidx.room.Query
import com.example.weatherapp.data.local.LatLng
import com.example.weatherapp.data.local.LatLngDao
import com.example.weatherapp.data.local.LatLngEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LatLngRepository  {

    suspend fun getAllUsers(): Flow<List<LatLng>>
    suspend fun insert(lat: Float, lng: Float)
    suspend fun delete(id: Long)
    suspend fun getBy(id: Long): LatLng?

}