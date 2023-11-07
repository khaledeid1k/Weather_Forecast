package com.kh.mo.weatherforecast.ui.map

import androidx.lifecycle.ViewModel
import com.kh.mo.weatherforecast.repo.Repo

class MapViewModel(private val repo:Repo) : ViewModel() {

    fun getAddressLocation(lat: Double, lon: Double,
                           getLocationData:(nameOfCity:String,
                                            nameOfCountry:String)->Unit){
        repo.getAddressLocation(lat, lon, getLocationData)
    }

}