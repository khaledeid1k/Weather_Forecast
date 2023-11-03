package com.kh.mo.weatherforecast.remot

import android.annotation.SuppressLint
import android.content.Context
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.remot.service.Network

object RemoteDataImp : RemoteData {
  private val netWork= Network.retrofitService
    override suspend fun getCurrentTemperature(): Weather? {
       return netWork.getWeatherData(32.32,32.32,"metric").body()

    }




}