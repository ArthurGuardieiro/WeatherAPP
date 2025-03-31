package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.LatLng
import com.example.weatherapp.data.local.LatLngDao
import com.example.weatherapp.data.local.LatLngEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LatLngRepositoryImpl @Inject constructor(
    private val latLngDao: LatLngDao
) : LatLngRepository {



    override suspend fun getAllUsers(): kotlinx.coroutines.flow.Flow<List<LatLng>> {
        return latLngDao.getAll().map { entities ->
            entities.map { entity ->
                LatLng(
                    id = entity.uid,
                    lat = entity.lat,
                    lng = entity.lng,
                    cityName = ""
                )
            }
        }
    }


    override suspend fun insert(lat: Float, lng: Float) {
        val entity = LatLngEntity(
            lat = lat,
            lng = lng
        )

        latLngDao.insertUser(entity)
    }

    override suspend fun delete(id: Long) {
        val existingEntity = latLngDao.getBy(id) ?: return
        latLngDao.delete(existingEntity)
    }

    override suspend fun getBy(id: Long): LatLng? {
        return latLngDao.getBy(id)?.let { entity ->
            LatLng(
                id = entity.uid,
                lat = entity.lat,
                lng = entity.lng,
                cityName = ""
            )
        }
    }


}