package com.kh.mo.weatherforecast.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.weatherforecast.repo.Repo

class MapViewModelFactory(private val repo:Repo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MapViewModel::class.java)){
            MapViewModel(repo) as T
        }else{
            throw java.lang.IllegalArgumentException("")
        }

    }

}