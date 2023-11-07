package com.kh.mo.weatherforecast.ui.initial

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InitialViewModel(private val repo:Repo) : ViewModel() {
    fun changeWayOfSelectLocationValue(wayOfSelectLocation: String){
        repo.changeWayOfSelectLocationValue(wayOfSelectLocation)
    }
    fun changeNotificationValue(isNotification: Boolean){
        repo.changeNotificationValue(isNotification)
    }





}





