package com.kh.mo.weatherforecast.remot

import android.annotation.SuppressLint
import android.content.Context
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.remot.service. Network
import retrofit2.Response

class RemoteDataImp private constructor(context: Context) : RemoteData {
  private val netWork= Network.getNetworkInstance(context).retrofitService



    override suspend fun getCurrentUpdatedWeatherState(latitude: Double,
                                                       longitude: Double): Response<Weather> {
       return netWork.getCurrentUpdatedWeatherState(latitude,longitude)

    }
    companion object{
        @Volatile
        private  var instance: RemoteDataImp?=null
        fun getRemoteDataImpInstance(context: Context): RemoteDataImp {
            return instance ?: synchronized(this){
                val instanceHolder= RemoteDataImp(context)
                instance =instanceHolder
                instanceHolder

            }
        }
    }




}