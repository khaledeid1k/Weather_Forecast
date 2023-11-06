package com.kh.mo.weatherforecast.remot

import android.annotation.SuppressLint
import android.content.Context
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.remot.service.Network

object RemoteDataImp : RemoteData {
  private val netWork= Network.retrofitService



    override suspend fun getCurrentTemperature( latitude: Double,
                                                longitude: Double): Weather? {
       return netWork.getWeatherData(latitude,longitude).body()

    }




}