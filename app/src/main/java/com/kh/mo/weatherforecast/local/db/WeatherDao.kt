package com.kh.mo.weatherforecast.local.db

import androidx.room.*
import com.kh.mo.weatherforecast.model.Favorite


@Dao
interface WeatherDao {

    suspend fun getWeatherOfCurrentDay()

    suspend fun insertWeatherOfCurrentDay()

    @Query("SELECT * FROM Favorite_Table")
    suspend fun getFavorites():List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite):Long

    @Delete
    suspend fun deleteFavorite(favorite: Favorite):Long

}