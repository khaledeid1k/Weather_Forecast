package com.kh.mo.weatherforecast.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.model.ui.WeatherState
import com.kh.mo.weatherforecast.repo.Repo
import com.kh.mo.weatherforecast.repo.mapper.convertWeatherToWeatherWeekData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: Repo) : ViewModel() {
    private val _weather = MutableStateFlow(WeatherState())
    val weather: StateFlow<WeatherState> = _weather


    fun getCurrentUpdatedWeatherState(locationData: LocationData) {
        saveLat(locationData.lat.toFloat())
        saveLon(locationData.lon.toFloat())
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrentUpdatedWeatherState(locationData.lat, locationData.lon)
                .collect {
                    val nameOfCity = getNameOfCity(it.lat, it.lon)
                    val currentTime = getCurrentTime()
                    val unit = getUnit()
                    _weather.value =
                        it.convertWeatherToWeatherWeekData(nameOfCity, currentTime, unit)
                }
        }
    }



    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean) {
        repo.changeValueOfFirstTimeOpenApp(isFirstTime)
    }

    fun checkIsFirstTimeOpenApp(): Boolean {
        return repo.checkIsFirstTimeOpenApp()
    }


    private fun getNameOfCity(lat: Double, lon: Double): String {
        var nameOfCity = ""
        repo.getAddressLocation(lat, lon) { city, _ ->
            nameOfCity = city
        }
        return nameOfCity
    }

    private fun getCurrentTime(): String = repo.getCurrentDate()
    private fun getUnit(): String = repo.getUnit()

    private fun saveLat(lat: Float){repo.setLat(lat)}
    private fun saveLon(lon: Float){repo.setLon(lon)}

     fun getLat(): Double {return repo.getLat()}
     fun getLon(): Double {return repo.getLon()}

}





