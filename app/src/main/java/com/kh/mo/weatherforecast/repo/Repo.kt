package com.kh.mo.weatherforecast.repo

import com.kh.mo.weatherforecast.model.Weather

interface Repo {
    suspend fun getCurrentTemperature() : Weather?
}