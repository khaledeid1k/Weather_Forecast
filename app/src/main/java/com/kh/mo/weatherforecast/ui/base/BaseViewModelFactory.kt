package com.kh.mo.weatherforecast.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.weatherforecast.SharedViewModel
import com.kh.mo.weatherforecast.repo.Repo
import com.kh.mo.weatherforecast.ui.favorites.FavouriteViewModel
import com.kh.mo.weatherforecast.ui.home.HomeViewModel
import com.kh.mo.weatherforecast.ui.initial.InitialViewModel
import com.kh.mo.weatherforecast.ui.map.MapViewModel
import com.kh.mo.weatherforecast.ui.setting.SettingsViewModel

class BaseViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            SettingsViewModel::class.java -> SettingsViewModel(repo) as T
            MapViewModel::class.java -> MapViewModel(repo) as T
            InitialViewModel::class.java -> InitialViewModel(repo) as T
            HomeViewModel::class.java -> HomeViewModel(repo) as T
            FavouriteViewModel::class.java -> FavouriteViewModel(repo) as T
            SharedViewModel::class.java -> SharedViewModel(repo) as T

            else -> throw Throwable("Unsupported view model")
        }
    }
}
