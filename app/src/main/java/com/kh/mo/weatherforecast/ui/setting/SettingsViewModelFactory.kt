package com.kh.mo.weatherforecast.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.weatherforecast.repo.Repo


class SettingsViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SettingsViewModel::class.java)){
            SettingsViewModel(repo) as T
        }else{
            throw java.lang.IllegalArgumentException("")
        }

    }

}