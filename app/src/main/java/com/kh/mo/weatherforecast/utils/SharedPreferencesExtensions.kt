package com.kh.mo.weatherforecast.utils

import android.content.SharedPreferences
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.ui.setting.Units
import com.kh.mo.weatherforecast.utils.Constants.NOTIFICATION

inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
    val editMe = edit()
    operation(editMe)
    editMe.apply()
}

var SharedPreferences.clearValues: () -> Unit
    get() = { this.edit().clear().apply() }
    set(value) {}


var SharedPreferences.isFirstTimeOpenApp
    get() = getBoolean(Constants.FIRST_TIME, true)
    set(value) {
        editMe {

            it.putBoolean(Constants.FIRST_TIME, value)
        }
    }

var SharedPreferences.lat
    get() = getString (Constants.LAT,"0.0")
    set(value) {
        editMe {
            it.putString(Constants.LAT, value)
        }
    }

var SharedPreferences.lon
    get() = getString(Constants.LON, "0.0")
    set(value) {
        editMe {
            it.putString(Constants.LON, value)
        }
    }

var SharedPreferences.tempUnit
    get() = getString(Constants.TEMP_UNIT, Units.Metric.nameOfUnit)
    set(value) {
        editMe {
            it.putString(Constants.TEMP_UNIT, value)
        }
    }





var SharedPreferences.language
    get() = getString(Constants.LANGUAGE, Language.en.name)
    set(value) {
        editMe {
            it.putString(Constants.LANGUAGE, value)
        }
    }

var SharedPreferences.windSpeed
    get() = getString(Constants.WIND_SPEED, Units.Metric.windSpeed)
    set(value) {

        editMe {
            it.putString(Constants.WIND_SPEED, value)
        }
    }



var SharedPreferences.wayOfSelectLocation
    get() = getString(Constants.LOCATION, Location.GPS.name)
    set(value) {
        editMe {
            it.putString(Constants.LOCATION, value)
        }
    }


var SharedPreferences.nameOfCity
    get() = getString(Constants.NAME_OF_CITY, "")
    set(value) {

        editMe {
            it.putString(Constants.NAME_OF_CITY, value)
        }
    }

var SharedPreferences.isNotificationAvailable
    get() = getBoolean(NOTIFICATION, true)
    set(value) {
        editMe {
            it.putBoolean(NOTIFICATION, value)
        }
    }
