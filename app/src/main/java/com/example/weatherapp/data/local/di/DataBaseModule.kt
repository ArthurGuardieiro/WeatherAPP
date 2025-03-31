package com.example.weatherapp.data.local.di

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.data.local.Database
import com.example.weatherapp.data.local.LatLngDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {


    @Provides
    @Singleton
    fun provideDataBaseDao(application: Application): LatLngDao {
        return Room.databaseBuilder(
            application,
            Database::class.java,
            "my_database"
        ).build().latLngDao()
    }
}