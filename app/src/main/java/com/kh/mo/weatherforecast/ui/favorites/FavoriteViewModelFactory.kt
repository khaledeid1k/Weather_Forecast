package com.kh.mo.weatherforecast.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.weatherforecast.repo.Repo

class FavoriteViewModelFactory(private val repo:Repo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FavouriteViewModel::class.java)){
            FavouriteViewModel(repo) as T
        }else{
            throw java.lang.IllegalArgumentException("")
        }

    }

}