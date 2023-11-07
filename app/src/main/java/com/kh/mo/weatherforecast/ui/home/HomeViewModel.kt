package com.kh.mo.weatherforecast.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repo:Repo) : ViewModel() {


    private var _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    private var _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String> = _currentDate

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    fun sendLocationData(locationData:LocationData) {
        viewModelScope.launch(Dispatchers.IO) {
            locationData.address?.let { _address.postValue(it) }
            _weather.postValue(repo.getCurrentTemperature(locationData.lat,locationData.lon))
        }
    }

    private fun setCurrentDate() {
        _currentDate.postValue(repo.getCurrentDate())
    }

    init {
        setCurrentDate()
    }

}





