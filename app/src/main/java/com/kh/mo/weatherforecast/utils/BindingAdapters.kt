package com.kh.mo.weatherforecast.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.Favorite
import com.kh.mo.weatherforecast.repo.mapper.convertToFavoriteEntity
import com.kh.mo.weatherforecast.repo.mapper.convertToFavorites
import com.kh.mo.weatherforecast.repo.mapper.convertToWeatherHoursData
import com.kh.mo.weatherforecast.repo.mapper.convertToWeatherWeekData
import com.kh.mo.weatherforecast.ui.favorites.FavouriteAdapter
import com.kh.mo.weatherforecast.ui.home.adapter.WeatherHourAdapter
import com.kh.mo.weatherforecast.ui.home.adapter.WeatherWeekAdapter
import java.util.Objects

@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(iconId: String?) {
    val iconUrl = iconId?.let { "https://openweathermap.org/img/wn/$it@2x.png" }
    Glide.with(this).load(iconUrl)
        .into(this)
}

@BindingAdapter(value = ["app:setItemsWeatherHourData"])
fun RecyclerView.setItemsWeatherHourData(weatherHourData:List<Hourly>?) {
    val weatherHourAdapter = WeatherHourAdapter()
    this.adapter = weatherHourAdapter
    weatherHourData?.let {
        it.convertToWeatherHoursData()
            .let { it1 -> weatherHourAdapter.setItems(it1) }
    }
}

@BindingAdapter(value = ["app:setItemsWeatherWeekData"])
fun RecyclerView.setItemsWeatherWeekData(weatherWeekData:List<Daily>?) {
    val weatherWeekAdapter = WeatherWeekAdapter()
    this.adapter = weatherWeekAdapter
    weatherWeekData?.let {
        it.convertToWeatherWeekData()
            .let { it1 -> weatherWeekAdapter.setItems(it1) }
    }
}





