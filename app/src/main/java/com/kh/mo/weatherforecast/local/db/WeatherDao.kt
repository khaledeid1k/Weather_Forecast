package com.kh.mo.weatherforecast.local.db

import androidx.room.*
import com.kh.mo.weatherforecast.model.entity.AlertEntity
import com.kh.mo.weatherforecast.model.entity.CurrentWeather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {
    @Query("SELECT * FROM WeatherState_Table WHERE type = :typeQ AND nameOfCity = :nameOfCityQ")
    suspend fun getSavedWeatherState(typeQ: String, nameOfCityQ: String): CurrentWeather?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherState(weatherState: CurrentWeather)

    @Update
    suspend fun updateWeatherState(weatherState: CurrentWeather)



    @Query("SELECT * FROM Favorite_Table")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM Favorite_Table WHERE nameOfCity = :nameOfCityQ")
    suspend fun deleteFavorite(nameOfCityQ: String)



    @Query("SELECT * FROM Alert_Table")
    fun getAlerts(): Flow<List<AlertEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAlert(alertEntity: AlertEntity)

    @Delete
    suspend fun deleteAlert(alertEntity: AlertEntity)


}