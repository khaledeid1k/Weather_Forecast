package com.kh.mo.weatherforecast.remot

import com.kh.mo.weatherforecast.model.Weather

interface RemoteData {
    suspend fun getCurrentTemperature(): Weather?
}