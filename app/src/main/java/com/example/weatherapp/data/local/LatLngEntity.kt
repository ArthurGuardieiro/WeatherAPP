package com.example.weatherapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "latLngEntity")
data class LatLngEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "lat") val lat: Float,
    @ColumnInfo(name = "lng") val lng: Float
)