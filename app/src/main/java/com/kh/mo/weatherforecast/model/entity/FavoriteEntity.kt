package com.kh.mo.weatherforecast.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import com.kh.mo.weatherforecast.ui.home.SourceOpenHome


@Entity(tableName = "Favorite_Table", primaryKeys = ["nameOfCity"])
data class FavoriteEntity(
    val lat: Double,
    val lon: Double,
    @NonNull
    val nameOfCity: String,
    val nameOfCountry:String,
    val type: SourceOpenHome?= SourceOpenHome.INITIAL_FRAGMENT,
)