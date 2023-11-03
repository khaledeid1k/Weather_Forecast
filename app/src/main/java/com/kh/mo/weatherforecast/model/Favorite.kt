package com.kh.mo.weatherforecast.model

import androidx.room.Entity

@Entity("Favorite_Table")
data class Favorite(val nameOfCity:String) {
}