package com.kh.mo.weatherforecast.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.model.ui.WeatherState
import com.kh.mo.weatherforecast.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repo:Repo) : ViewModel() {


    private var _weather = MutableLiveData<WeatherState>()
    val weather: LiveData<WeatherState> = _weather


    fun sendLocationData(locationData:LocationData) {
        viewModelScope.launch(Dispatchers.IO) {
            _weather.postValue(repo.getCurrentWeatherState(locationData.lat,locationData.lon))
        }
    }
    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean){
        repo.changeValueOfFirstTimeOpenApp(isFirstTime)
    }

    fun checkIsFirstTimeOpenApp(): Boolean {
       return repo.checkIsFirstTimeOpenApp()
    }





}





