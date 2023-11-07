package com.kh.mo.weatherforecast.remot

import android.annotation.SuppressLint
import android.content.Context
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.remot.service.Network
import retrofit2.Response

object RemoteDataImp : RemoteData {
  private val netWork= Network.retrofitService



    override suspend fun getCurrentWeatherState( latitude: Double,
                                                longitude: Double): Response<Weather> {
       return netWork.getCurrentWeatherState(latitude,longitude)

    }




}