package com.kh.mo.weatherforecast.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.Favorite
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.repo.Repo
import com.kh.mo.weatherforecast.repo.mapper.convertWeatherToWeatherWeekData
import com.kh.mo.weatherforecast.repo.mapper.convertListOfFavoriteEntityToFavorites
import com.kh.mo.weatherforecast.repo.mapper.convertWeatherToFavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class FavouriteViewModel(private val repo: Repo) : ViewModel(), FavouriteAdapter.FavouriteListener {
    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> = _favorites

    fun getWeatherStateOfLocation(locationData: LocationData) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrentUpdatedWeatherState(locationData.lat, locationData.lon)
                .collect {
                    val nameOfCity = getNameOfCity(it.lat, it.lon)
                    val currentTime = getCurrentTime()
                    val unit = getUnit()
                    saveFavorite(it.convertWeatherToFavoriteEntity(nameOfCity, currentTime,unit))
                }
        }
    }

    private fun getNameOfCity(lat: Double, lon: Double): String {
        var nameOfCity = ""
        repo.getAddressLocation(lat, lon) { city, _ ->
            nameOfCity = city
        }
        return nameOfCity
    }

    private fun getCurrentTime(): String = repo.getCurrentDate()
    private fun getUnit(): String = repo.getUnit()


    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getFavorites().collect {
                _favorites.value = it.convertListOfFavoriteEntityToFavorites()
            }

        }
    }

    init {
        getFavorites()
    }

    private fun saveFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveFavorite(favorite)

        }
    }


    private fun deleteFavorite(favoriteName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteFavorite(favoriteName)
        }


    }

    override fun onClickFavourite(favorite: Favorite) {

    }

    override fun deleteFavourite(favorite: Favorite) {
        deleteFavorite(favorite.nameOfCity)

    }
}