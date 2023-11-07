package com.kh.mo.weatherforecast.local.db

import androidx.room.*
import com.kh.mo.weatherforecast.model.entity.Favorite
import com.kh.mo.weatherforecast.model.ui.WeatherState


@Dao
interface WeatherDao {
    @Query("SELECT * FROM WeatherState_Table WHERE lan = :latitude AND lon = :longitude")
    suspend fun getWeatherState(latitude: Double,
                                longitude: Double):WeatherState

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherState(weatherState: WeatherState)

    @Update
    suspend fun updateWeatherState(weatherState: WeatherState)

    @Delete
    suspend fun deleteWeatherState(weatherState: WeatherState)

    @Query("SELECT * FROM Favorite_Table")
    suspend fun getFavorites():List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

}