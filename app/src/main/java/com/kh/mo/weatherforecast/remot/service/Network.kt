package com.kh.mo.weatherforecast.remot.service

import android.content.Context
import androidx.room.Room
import com.kh.mo.weatherforecast.BuildConfig
import com.kh.mo.weatherforecast.local.db.WeatherDataBase
import com.kh.mo.weatherforecast.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network  private constructor(context: Context){
    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(context))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitService : Service by lazy {
        retrofit.create(Service::class.java)
    }


    companion object{
        @Volatile
        private  var instance: Network?=null
        fun getNetworkInstance(context: Context): Network {
            return instance ?: synchronized(this){
                val instanceHolder= Network(context)
                instance =instanceHolder
                instanceHolder

            }
        }
    }
}