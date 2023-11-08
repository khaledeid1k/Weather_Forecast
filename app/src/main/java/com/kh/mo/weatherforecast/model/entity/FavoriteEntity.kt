package com.kh.mo.weatherforecast.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly

@Entity(tableName = "Favorite_Table", primaryKeys = ["lan", "lon"])
data class FavoriteEntity(
    val lan: Double,
    val lon: Double,
    val nameOfCity :String,
    val currentTime :String,
    val temp :Double,
    val unit :String,
    val tempDescription :String,
    val icon :String,
    val humidity: Int,
    val clouds: Int,
    val wind_speed: Double,
    val pressure: Int,
    val hourly: List<Hourly>,
    val daily: List<Daily>

)