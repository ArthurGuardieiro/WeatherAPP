package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LatLngDao {

    @Query("SELECT * FROM latlngentity")
    fun getAll() : List<LatLngEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(latLngEntity: LatLngEntity)

}