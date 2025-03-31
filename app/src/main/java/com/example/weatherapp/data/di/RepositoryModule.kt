package com.example.weatherapp.data.di

import com.example.weatherapp.data.repository.LatLngRepository
import com.example.weatherapp.data.repository.LatLngRepositoryImpl
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindWeatherRepository(repositoryImpl: WeatherRepositoryImpl) : WeatherRepository

    @Binds
    fun bindLatLngRepository(repositoryImpl: LatLngRepositoryImpl) : LatLngRepository
}