package com.kh.mo.weatherforecast

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kh.mo.weatherforecast.repo.Repo

class SharedViewModel(private val repo: Repo):ViewModel() {


    fun changeLanguageApp(){
        repo.changeLanguageApp(repo.getLanguage())
    }

    fun checkIsFirstTimeOpenApp()=repo.checkIsFirstTimeOpenApp()

}