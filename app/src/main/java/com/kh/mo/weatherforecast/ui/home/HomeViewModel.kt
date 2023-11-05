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

    val locationDataChange = MutableLiveData<LocationData>()

    fun getLocationData() {
        viewModelScope.launch(Dispatchers.IO) {
            locationDataChange.value?.apply {
                _weather.postValue(repo.getCurrentTemperature(lat, lon))
            }
        }
    }
}





