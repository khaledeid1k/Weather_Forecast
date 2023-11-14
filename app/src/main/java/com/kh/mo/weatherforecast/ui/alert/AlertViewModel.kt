package com.kh.mo.weatherforecast.ui.alert

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kh.mo.weatherforecast.model.entity.AlertEntity
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.repo.Repo
import com.kh.mo.weatherforecast.repo.mapper.convertFavoriteEntityToLocationData
import com.kh.mo.weatherforecast.ui.home.SourceOpenHome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlertViewModel(private val repo: Repo) : ViewModel(),
    AlertCountryAdapter.AlertCountryListener,AlertAdapter.AlertListener {
    private val _favorites = MutableStateFlow<List<FavoriteEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteEntity>> = _favorites

    private val _favoritesEvent = MutableLiveData<LocationData>()
    val favoritesEvent: LiveData<LocationData> = _favoritesEvent


    private val _alerts = MutableStateFlow<List<AlertEntity>>(emptyList())
    val alerts: StateFlow<List<AlertEntity>> = _alerts


    init {
        getAlerts()
    }
  private  fun getAlerts(){
        viewModelScope.launch {
            repo.getAlerts().collect{
                _alerts.value=it
            }
        }
    }
    fun requestFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getFavorites().collect {
                _favorites.value = it
            }

        }
    }

    fun saveAlert(alertEntity: AlertEntity){
        viewModelScope.launch {
            repo.saveAlert(alertEntity)
        }
    }


    override fun onClickFavourite(favorite: FavoriteEntity) {
        _favoritesEvent.postValue(favorite.convertFavoriteEntityToLocationData(SourceOpenHome.FAVORITE_FRAGMENT))
    }
    override fun deleteAlert(alertEntity: AlertEntity) {
        viewModelScope.launch {
            repo.deleteAlert(alertEntity)
//            val intent = Intent(context, AlarmReceiver::class.java)
//            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
//            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.cancel(pendingIntent)
        }
    }


}