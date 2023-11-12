package com.kh.mo.weatherforecast.repo

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kh.mo.weatherforecast.data.local.FakeLocalDataSource
import com.kh.mo.weatherforecast.data.remote.FakeRemoteDataSource
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather
import com.kh.mo.weatherforecast.model.*
import com.kh.mo.weatherforecast.model.entity.CurrentWeather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.WeatherHourData
import com.kh.mo.weatherforecast.model.ui.WeatherWeekData
import com.kh.mo.weatherforecast.remot.ApiSate
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.properties.Delegates


@RunWith(AndroidJUnit4::class)
class RepoImTest {

    private lateinit var fakeRemoteDataSource: FakeRemoteDataSource
    private lateinit var fakeLocalDataSource: FakeLocalDataSource
    private lateinit var currentWeather: CurrentWeather
    private lateinit var repo: RepoIm
    private lateinit var favorites: MutableList<FavoriteEntity>
    private lateinit var sharedPreferences: SharedPreferencesWeather

    private var lat by Delegates.notNull<Double>()
    private var lon by Delegates.notNull<Double>()


    private fun fakeData() {
        val weatherHourData = WeatherHourData(
            hour = "12:00 PM",
            weatherIcon = "icon_sunny",
            temperature = 25.0,
            unit = "Celsius"
        )

        val weatherWeekData = WeatherWeekData(
            nameOfDay = "Monday",
            weatherIcon = "icon_partly_cloudy",
            description = "Partly Cloudy",
            temperature = 22.5,
            unit = "Celsius"
        )

        currentWeather = CurrentWeather(
            lan = 37.7749,
            lon = -122.4194,
            nameOfCity = "San Francisco",
            nameOfCountry = "USA",
            currentTime = "2023-11-12T12:00:00",
            temp = 23.0,
            unit = "Celsius",
            tempDescription = "Partly Cloudy",
            icon = "icon_partly_cloudy",
            humidity = 65,
            clouds = 30,
            wind_speed = 10.0,
            pressure = 1010,
            hourly = listOf(weatherHourData),
            daily = listOf(weatherWeekData),
            type = "current"
        )

        favorites = mutableListOf()
        sharedPreferences = SharedPreferencesWeather(ApplicationProvider.getApplicationContext())
    }


    init {
        fakeData()
    }

    @Before
    fun setUp() {

        fakeRemoteDataSource = FakeRemoteDataSource()
        fakeLocalDataSource = FakeLocalDataSource(currentWeather, favorites, sharedPreferences)
        repo = RepoIm.getRepoImInstance(fakeLocalDataSource, fakeRemoteDataSource)
    }



    @Test
    fun getCurrentUpdatedWeatherState_NotValidLatAndLon_FailureWeather() = runBlockingTest {
        repo.getCurrentUpdatedWeatherState(null, null).collect {
            when (it) {
                is ApiSate.Success -> assertThat(it.data, IsEqual(""))
                ApiSate.Loading -> {}
                is ApiSate.Failure -> assertThat(it.msg, IsEqual("body == null"))
            }


        }

    }





}