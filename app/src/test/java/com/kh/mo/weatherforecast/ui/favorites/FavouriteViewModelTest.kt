package com.kh.mo.weatherforecast.ui.favorites

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kh.mo.weatherforecast.data.FakeRepo
import com.kh.mo.weatherforecast.data.local.FakeLocalDataSource
import com.kh.mo.weatherforecast.data.remote.FakeRemoteDataSource
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather
import com.kh.mo.weatherforecast.model.entity.CurrentWeather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.WeatherHourData
import com.kh.mo.weatherforecast.model.ui.WeatherWeekData
import com.kh.mo.weatherforecast.ui.home.SourceOpenHome
import com.kh.mo.weatherforecast.ui.setting.SettingsViewModel
import com.kh.mo.weatherforecast.utils.wayOfSelectLocation
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavouriteViewModelTest {
    private lateinit var fakeRemoteDataSource: FakeRemoteDataSource
    private lateinit var fakeLocalDataSource: FakeLocalDataSource
    private lateinit var currentWeather: CurrentWeather
    private lateinit var repo: FakeRepo
    private lateinit var favorites: MutableList<FavoriteEntity>
    private lateinit var sharedPreferences: SharedPreferencesWeather
    private lateinit var viewModel: FavouriteViewModel


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

        favorites = mutableListOf(
            FavoriteEntity(0.0, 0.0, "", "", SourceOpenHome.INITIAL_FRAGMENT),
            FavoriteEntity(0.0, 0.0, "", "", SourceOpenHome.INITIAL_FRAGMENT),
            FavoriteEntity(0.0, 0.0, "", "", SourceOpenHome.INITIAL_FRAGMENT),

            )
        sharedPreferences = SharedPreferencesWeather(ApplicationProvider.getApplicationContext())

        sharedPreferences.customPreference().wayOfSelectLocation = "MAP"


    }


    init {
        fakeData()
    }

    @Before
    fun setUp() {
        fakeRemoteDataSource = FakeRemoteDataSource()
        fakeLocalDataSource = FakeLocalDataSource(currentWeather, favorites, sharedPreferences)
        repo = FakeRepo(fakeLocalDataSource, fakeRemoteDataSource)
        viewModel= FavouriteViewModel(repo)
    }


    @Test
    fun `test favorites state flow`() = runBlockingTest {
        var collectedValues : List<FavoriteEntity> = emptyList()
        val job = launch {
            viewModel.favorites.collect {
                collectedValues=it
            }
        }
        assertThat(favorites, IsEqual(collectedValues))
        job.cancel()
    }


}