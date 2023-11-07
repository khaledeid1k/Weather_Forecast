package com.kh.mo.weatherforecast.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(private val repo:Repo) : ViewModel() {

    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean){
        repo.changeValueOfFirstTimeOpenApp(isFirstTime)
    }

}





