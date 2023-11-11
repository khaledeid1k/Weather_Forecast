package com.kh.mo.weatherforecast.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.weatherforecast.model.entity.CurrentWeather
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.remot.ApiSate
import com.kh.mo.weatherforecast.repo.Repo
import com.kh.mo.weatherforecast.repo.mapper.convertWeatherToCurrentWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: Repo) : ViewModel() {
    private val _weather = MutableStateFlow<ApiSate<CurrentWeather>>(ApiSate.Loading)
    val weather: StateFlow<ApiSate<CurrentWeather>> = _weather


    fun getCurrentUpdatedWeatherState(locationData: LocationData) {

        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrentUpdatedWeatherState(locationData.lat, locationData.lon)
                .collect {
                    when (it) {
                        is ApiSate.Failure -> {

                            locationData.nameOfCity?.let { it1 ->
                                Log.d("TAG", "$it1 getCurrentUpdatedWeatherState: ")
                                locationData.type?.name?.let { it2 ->
                                    getSavedWeatherState(
                                        it2,
                                        it1
                                    )
                                }
                            }
                        }
                        ApiSate.Loading -> {
                            _weather.value = ApiSate.Loading
                        }
                        is ApiSate.Success -> {
                            val data = it.data
                            saveDataIfFirstTimeOpenApp(locationData)
                            val nameOfCity = getNameOfCity(data.lat, data.lon).first
                            val nameOfCountry = getNameOfCity(data.lat, data.lon).second
                            val currentTime = getCurrentTime()
                            val unit = getUnit()
                            val currentWeather =
                                data.convertWeatherToCurrentWeather(
                                    nameOfCity,
                                    nameOfCountry,
                                    currentTime,
                                    unit, locationData.type!!.name
                                )
                            saveCurrentWeather(currentWeather)
                            _weather.value = ApiSate.Success(currentWeather)

                        }
                    }


                }
        }
    }

    private fun saveDataIfFirstTimeOpenApp(locationData: LocationData) {
        if (locationData.type == SourceOpenHome.INITIAL_FRAGMENT) {
            saveLat(locationData.lat)
            saveLon(locationData.lon)
            Log.d("TAG", "saveDataIfFirstTimeOpenApp: ${locationData.nameOfCity}")
            setCityName(locationData.nameOfCity.toString())
        }
    }


    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean) {
        repo.changeValueOfFirstTimeOpenApp(isFirstTime)
    }


    private fun getNameOfCity(lat: Double, lon: Double): Pair<String, String> {
        var nameOfCity = ""
        var nameOfCountry = ""
        repo.getAddressLocation(lat, lon) { city, country ->
            nameOfCity = city
            nameOfCountry = country
        }
        return Pair(nameOfCity, nameOfCountry)
    }

    private fun getCurrentTime(): String = repo.getCurrentDate()
    private fun getUnit(): String = repo.getTempUnit()

    private fun saveLat(lat: Double) {
        repo.setLat(lat)
    }

    private fun saveLon(lon: Double) {
        repo.setLon(lon)
    }

    fun getLat(): Double {
        return repo.getLat()
    }

    fun getLon(): Double {
        return repo.getLon()
    }

    fun getCityName(): String {
        return repo.getCityName()
    }

    private fun setCityName(nameOfCity:String){
        repo.setCityName(nameOfCity)
    }

    private fun saveCurrentWeather(currentWeather: CurrentWeather) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveWeatherState(currentWeather)
        }
    }

    private fun getSavedWeatherState(type:String,nameOfCity:String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSavedWeatherState(type,nameOfCity).collect {
                when (it) {
                    is ApiSate.Failure -> {
                        _weather.value = ApiSate.Failure(it.msg)
                    }
                    is ApiSate.Loading -> {
                        _weather.value = ApiSate.Loading
                    }
                    is ApiSate.Success -> {
                        _weather.value = ApiSate.Success(it.data)
                    }
                }

            }
        }
    }
}





