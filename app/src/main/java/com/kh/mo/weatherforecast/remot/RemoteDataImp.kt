package com.kh.mo.weatherforecast.remot

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.remot.service. Network
import retrofit2.Response
import java.util.*

class RemoteDataImp private constructor(val context: Context) : RemoteData {
  private val netWork= Network.getNetworkInstance(context).retrofitService



    override suspend fun getCurrentUpdatedWeatherState(latitude: Double,
                                                       longitude: Double): Response<Weather> {
       return netWork.getCurrentUpdatedWeatherState(latitude,longitude)

    }

    override fun getAddressLocation(
        lat: Double,
        lon: Double,
        language:String,
        getLocationData: (nameOfCity: String, nameOfCountry: String) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale(language))
        try {


            val addresses = geocoder.getFromLocation(
                lat, lon, 1
                ,)

            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                val fullAddress = address.getAddressLine(0)
                val addressData = fullAddress.split(",")
                var nameOfCity = addressData[0]
                if (addresses.size > 1) {
                    nameOfCity = addressData[1]
                }
                val nameOfCountry = addressData[addressData.size - 1]
                getLocationData(nameOfCity, nameOfCountry)

            }
        }catch (e:Exception){}
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