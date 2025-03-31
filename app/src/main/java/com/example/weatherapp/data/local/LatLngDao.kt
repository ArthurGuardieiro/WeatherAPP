package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LatLngDao {

    @Query("SELECT * FROM latlngentity")
    fun getAll() : Flow<List<LatLngEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(latLngEntity: LatLngEntity)

    @Delete
    suspend fun delete(entity: LatLngEntity)

    @Query("SELECT * FROM latLngEntity WHERE uid = :id")
    suspend fun getBy(id: Long): LatLngEntity

}