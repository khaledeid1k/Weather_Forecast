package com.kh.mo.weatherforecast.local

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.kh.mo.weatherforecast.local.LocalData
import com.kh.mo.weatherforecast.local.db.WeatherDataBase
import com.kh.mo.weatherforecast.utils.Constants

class LocalDataImp private constructor(context: Context) :LocalData {
    private val dataBase= WeatherDataBase.getWeatherDataBaseInstance(context)?.weatherDao()

    override suspend fun getWeatherOfCurrentDay() {
        dataBase?.getWeatherOfCurrentDay()
    }
    companion object{
        @Volatile
        private  var instance: LocalDataImp?=null
        fun getLocalDataImpInstance( context: Context): LocalDataImp? {
            return instance ?: synchronized(this){
                val instanceHolder=LocalDataImp(context)
                instance =instanceHolder
                instance

            }
        }
    }

}