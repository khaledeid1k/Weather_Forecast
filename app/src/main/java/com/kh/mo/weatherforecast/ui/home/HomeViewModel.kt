package com.kh.mo.weatherforecast.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.model.ui.WeatherState
import com.kh.mo.weatherforecast.repo.Repo
import com.kh.mo.weatherforecast.repo.mapper.convertWeatherToWeatherWeekData
import com.kh.mo.weatherforecast.utils.Constants.INITIAL_FRAGMENT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: Repo) : ViewModel() {
    private val _weather = MutableStateFlow(WeatherState())
    val weather: StateFlow<WeatherState> = _weather


    fun getCurrentUpdatedWeatherState(locationData: LocationData) {

        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrentUpdatedWeatherState(locationData.lat, locationData.lon)
                .collect {
                    checkOpenFromInitialFragment(locationData)
                    val nameOfCity = getNameOfCity(it.lat, it.lon)
                    val currentTime = getCurrentTime()
                    val unit = getUnit()
                    _weather.value =
                        it.convertWeatherToWeatherWeekData(nameOfCity, currentTime, unit)
                }
        }
    }

     private fun checkOpenFromInitialFragment(locationData: LocationData){
        if(locationData.type==INITIAL_FRAGMENT){
        saveLat(locationData.lat.toFloat())
        saveLon(locationData.lon.toFloat())
        }
    }



    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean) {
        repo.changeValueOfFirstTimeOpenApp(isFirstTime)
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





