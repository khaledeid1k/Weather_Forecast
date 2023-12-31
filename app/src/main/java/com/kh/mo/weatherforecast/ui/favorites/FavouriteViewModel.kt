package com.kh.mo.weatherforecast.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.repo.Repo
import com.kh.mo.weatherforecast.repo.mapper.convertFavoriteEntityToLocationData
import com.kh.mo.weatherforecast.repo.mapper.convertLocationDataToFavoriteEntity
import com.kh.mo.weatherforecast.ui.home.SourceOpenHome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class FavouriteViewModel(private val repo: Repo) : ViewModel(), FavouriteAdapter.FavouriteListener {
    private val _favorites = MutableStateFlow<List<FavoriteEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteEntity>> = _favorites

    private val _favoritesEvent = MutableLiveData<LocationData>()
    val favoritesEvent: LiveData<LocationData> = _favoritesEvent


    private val _deleteFavorite = MutableLiveData<String>()
    val deleteFavorite: LiveData<String> = _deleteFavorite

    init {
        getFavorites()
    }


    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getFavorites().collect {
                _favorites.value = it
            }

        }
    }


     fun saveFavorite(favorite: LocationData) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveFavorite(favorite.convertLocationDataToFavoriteEntity())

        }
    }


     fun deleteFavorite(favoriteName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteFavorite(favoriteName)
        }


    }

    override fun onClickFavourite(favorite: FavoriteEntity) {
        _favoritesEvent.postValue(favorite.convertFavoriteEntityToLocationData(SourceOpenHome.FAVORITE_FRAGMENT))

    }

    override fun deleteFavourite(favorite: FavoriteEntity) {
        _deleteFavorite.postValue(favorite.nameOfCity)
    }
}