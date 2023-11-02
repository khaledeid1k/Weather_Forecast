package com.kh.mo.weatherforecast.local.db

import androidx.room.Dao


@Dao
interface WeatherDao {

    suspend fun getWeatherOfCurrentDay()
    suspend fun insertWeatherOfCurrentDay()
    suspend fun getFavorites()
    suspend fun insertFavorites()
    suspend fun deleteFavorites()
    suspend fun updateFavorites()
}