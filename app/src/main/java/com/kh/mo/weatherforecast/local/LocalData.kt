package com.kh.mo.weatherforecast.local

interface LocalData {
    suspend fun getWeatherOfCurrentDay()
    suspend fun insertWeatherOfCurrentDay()
    suspend fun getFavorites()
    suspend fun insertFavorites()
    suspend fun deleteFavorites()
    suspend fun updateFavorites()
}