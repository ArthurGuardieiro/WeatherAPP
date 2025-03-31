package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.LatLngDao
import com.example.weatherapp.data.local.LatLngEntity
import javax.inject.Inject

class LatLngRepositoryImpl @Inject constructor(
    private val latLngDao: LatLngDao
) : LatLngRepository {

    override fun getAllUsers(): List<LatLngEntity> {

        return latLngDao.getAll()
    }

    override fun insertUser(LatLng: LatLngEntity) {
        latLngDao.insertUser(LatLng)
    }

}