package com.kh.mo.weatherforecast.repo

import android.annotation.SuppressLint
import com.kh.mo.weatherforecast.local.LocalData
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.remot.RemoteData

class RepoIm private constructor(
    private val localData: LocalData?,
    private val remoteData: RemoteData
) : Repo {

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: RepoIm? = null
        fun getRepoImInstance(localData: LocalDataImp?, remoteData: RemoteData): RepoIm? {
            return instance ?: synchronized(this) {
                val instanceHolder = RepoIm(localData, remoteData)
                instance = instanceHolder
                instance

            }
        }
    }

    override suspend fun getCurrentTemperature(): Weather? {
        return remoteData.getCurrentTemperature()
    }
}