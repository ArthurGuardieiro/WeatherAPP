package com.example.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LatLngEntity::class], version = 1)
abstract class Database: RoomDatabase() {


    abstract fun latLngDao(): LatLngDao

}