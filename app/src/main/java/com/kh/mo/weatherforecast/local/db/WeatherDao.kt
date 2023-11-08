package com.kh.mo.weatherforecast.local.db

import androidx.room.*
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.entity.WeatherEntity
import com.kh.mo.weatherforecast.model.ui.Favorite
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {
    @Query("SELECT * FROM WeatherState_Table WHERE lan = :latitude AND lon = :longitude")
     fun getWeatherState(latitude: Double,
                                longitude: Double):Flow<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherState(weatherState: WeatherEntity)

    @Update
    suspend fun updateWeatherState(weatherState: WeatherEntity)

    @Delete
    suspend fun deleteWeatherState(weatherState: WeatherEntity)

    @Query("SELECT * FROM Favorite_Table")
     fun getFavorites():Flow< List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(favorite: FavoriteEntity)


    @Query("DELETE FROM Favorite_Table WHERE nameOfCity = :favoriteName")
    suspend fun deleteFavorite(favoriteName: String)

    @Query("SELECT * FROM Favorite_Table WHERE nameOfCity = :favoriteName")
     fun getFavorite(favoriteName: String):Flow <FavoriteEntity?>

}