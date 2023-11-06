package com.kh.mo.weatherforecast.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kh.mo.weatherforecast.model.entity.Favorite
import com.kh.mo.weatherforecast.utils.Constants.DATA_BASE

@Database(entities = [Favorite::class], version = 1)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao


    companion object{
        @Volatile
        private  var instance: WeatherDataBase?=null

        fun getWeatherDataBaseInstance(context: Context): WeatherDataBase? {
            return instance ?: synchronized(this){
               val instanceHolder=  Room.databaseBuilder(context.applicationContext,
                WeatherDataBase::class.java,DATA_BASE).build()
                instance =instanceHolder
                instance

            }
        }
    }
}