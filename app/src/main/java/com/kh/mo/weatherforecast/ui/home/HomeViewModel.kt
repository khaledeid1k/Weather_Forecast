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

    val locationDataChange = MutableLiveData<LocationData>()

    val address: LiveData<LocationData> = locationDataChange


    fun getWeatherState() {
        viewModelScope.launch(Dispatchers.IO) {
            locationDataChange.value?.apply {
                _weather.postValue(repo.getCurrentTemperature(lat, lon))
            }
        }
    }

    private fun currentDate() {
        _currentDate.postValue(repo.getCurrentDate())
    }

    init {
        currentDate()
    }

}





