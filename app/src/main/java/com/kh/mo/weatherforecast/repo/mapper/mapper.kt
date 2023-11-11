package com.kh.mo.weatherforecast.repo.mapper

import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.model.entity.CurrentWeather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.model.ui.WeatherHourData
import com.kh.mo.weatherforecast.model.ui.WeatherWeekData
import com.kh.mo.weatherforecast.ui.setting.Units
import com.kh.mo.weatherforecast.utils.convertUnixTimestampToDateTime

fun List<Hourly>.convertListOfHourlyToWeatherHoursData(unit:String): List<WeatherHourData> {
    return this.map {
        WeatherHourData(
            it.dt.convertUnixTimestampToDateTime(),
            it.weather[0].icon,
            it.temp,
            unit
        )
    }
}

fun List<Daily>.convertWeatherToWeatherWeekData(dayNames:List<String>,unit:String): List<WeatherWeekData> {
    return this.drop(1).mapIndexed { index, daily ->
        WeatherWeekData(
            index.takeIf { index < this.size }.let { dayNames[it!!] },
            daily.weather[0].icon,
            daily.weather[0].description,
            daily.temp.day,
            unit
        )
    }
}

fun Weather.convertWeatherToCurrentWeather(
    nameOfCity: String, nameOfCountry: String, currentTime: String,
    unit: String, type: String,dayNames:List<String>
): CurrentWeather {
    return this.let {
        CurrentWeather(
            it.lat,
            it.lon,
            nameOfCity,
            nameOfCountry,
            currentTime,
            it.current.temp,
            unit,
            it.current.weather[0].description,
            it.current.weather[0].icon,
            it.current.humidity,
            it.current.clouds,
            it.current.wind_speed,
            it.current.pressure,
            hourly.convertListOfHourlyToWeatherHoursData(unit),
            daily.convertWeatherToWeatherWeekData(dayNames,unit),
            type

        )


    }
}

fun LocationData.convertListOfFavoriteEntityToFavorites(): FavoriteEntity {
    return FavoriteEntity(
        lat,lon,nameOfCity!!, nameOfCountry!!,type

    )

}

fun String.convertUnitToTempUnit():String{
  return  when(this){
        Units.Standard.nameOfUnit->"°K"
        Units.Imperial.nameOfUnit->"°F"
        Units.Metric.nameOfUnit->"°C"
        else -> ""
    }
}




