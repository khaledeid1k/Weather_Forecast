package com.kh.mo.weatherforecast.ui.initial

import androidx.lifecycle.ViewModel
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.repo.Repo

class InitialViewModel(private val repo:Repo) : ViewModel() {
    fun setLocation(location: Location) {
        repo.setLocation(location)
    }



    fun changeNotificationValue(isNotification: Boolean){
        repo.changeNotificationValue(isNotification)
    }

    fun getAddressLocation(lat: Double, lon: Double,
                           getLocationData:(nameOfCity:String,
                                            nameOfCountry:String)->Unit){
        repo.getAddressLocation(lat, lon, getLocationData)
    }





}





