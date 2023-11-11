package com.kh.mo.weatherforecast.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly
import com.kh.mo.weatherforecast.remot.ApiSate
import com.kh.mo.weatherforecast.repo.mapper.convertListOfHourlyToWeatherHoursData
import com.kh.mo.weatherforecast.repo.mapper.convertWeatherToWeatherWeekData
import com.kh.mo.weatherforecast.ui.home.adapter.WeatherHourAdapter
import com.kh.mo.weatherforecast.ui.home.adapter.WeatherWeekAdapter

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
        it.convertListOfHourlyToWeatherHoursData()
            .let { it1 -> weatherHourAdapter.setItems(it1) }
    }
}

@BindingAdapter(value = ["app:setItemsWeatherWeekData"])
fun RecyclerView.setItemsWeatherWeekData(weatherWeekData:List<Daily>?) {
    val weatherWeekAdapter = WeatherWeekAdapter()
    this.adapter = weatherWeekAdapter
    weatherWeekData?.let {
        it.convertWeatherToWeatherWeekData()
            .let { it1 -> weatherWeekAdapter.setItems(it1) }
    }
}


@BindingAdapter("app:showWhenLoading")
fun <T> View.showWhenLoading(apiSate: ApiSate<T>) {
    if (apiSate is ApiSate.Loading) makeVisible() else makeGone()
}

@BindingAdapter("app:showWhenError")
fun <T> View.showWhenError(apiSate: ApiSate<T>) {
    if (apiSate is ApiSate.Failure) makeVisible() else makeGone()

}

@BindingAdapter("app:showWhenSuccess")
fun <T> View.showWhenSuccess(apiSate: ApiSate<T>) {
    if (apiSate is ApiSate.Success) makeVisible() else makeGone()

}






