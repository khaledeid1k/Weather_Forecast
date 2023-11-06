package com.kh.mo.weatherforecast.ui.initial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.weatherforecast.repo.Repo

class InitialViewModelFactory(private val repo:Repo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(InitialViewModel::class.java)){
            InitialViewModel(repo) as T
        }else{
            throw java.lang.IllegalArgumentException("")
        }

    }

}