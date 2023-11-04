package com.kh.mo.weatherforecast.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Favorite_Table")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
     val id: Int,
    val nameOfCity: String
)