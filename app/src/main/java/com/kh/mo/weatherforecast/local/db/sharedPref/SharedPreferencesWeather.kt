package com.kh.mo.weatherforecast.local.db.sharedPref

import android.content.Context
import android.content.SharedPreferences
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.ui.setting.Units
import com.kh.mo.weatherforecast.utils.Constants.FIRST_TIME
import com.kh.mo.weatherforecast.utils.Constants.GPS
import com.kh.mo.weatherforecast.utils.Constants.LANGUAGE
import com.kh.mo.weatherforecast.utils.Constants.LAT
import com.kh.mo.weatherforecast.utils.Constants.LOCATION
import com.kh.mo.weatherforecast.utils.Constants.LON
import com.kh.mo.weatherforecast.utils.Constants.NAME_OF_CITY
import com.kh.mo.weatherforecast.utils.Constants.NOTIFICATION
import com.kh.mo.weatherforecast.utils.Constants.PREFERENCE_NAME
import com.kh.mo.weatherforecast.utils.Constants.TEMP_UNIT
import com.kh.mo.weatherforecast.utils.Constants.WAY_LOCATION
import com.kh.mo.weatherforecast.utils.Constants.WIND_SPEED

object SharedPreferencesWeather {

    fun customPreference(context: Context): SharedPreferences =

        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }
    var SharedPreferences.clearValues: () -> Unit
        get() = { this.edit().clear().apply() }
        set(value) {}

    var SharedPreferences.wayForSelectLocation
        get() = getString(WAY_LOCATION,GPS)
        set(value) {
            editMe {
                it.putString(WAY_LOCATION, value)
            }
        }
    var SharedPreferences.isFirstTimeOpenApp
        get() = getBoolean(FIRST_TIME, true)
        set(value) {
            editMe {

                it.putBoolean(FIRST_TIME, value)
            }
        }

    var SharedPreferences.lat
        get() = getFloat (LAT,0f)
        set(value) {
            editMe {
                it.putFloat(LAT, value)
            }
        }

    var SharedPreferences.lon
        get() = getFloat(LON, 0f)
        set(value) {
            editMe {
                it.putFloat(LON, value)
            }
        }

    var SharedPreferences.tempUnit
        get() = getString(TEMP_UNIT,Units.Metric.nameOfUnit)
        set(value) {
            editMe {
                it.putString(TEMP_UNIT, value)
            }
        }


    var SharedPreferences.isNotificationAvailable
        get() = getBoolean(NOTIFICATION, true)
        set(value) {
            editMe {
                it.putBoolean(NOTIFICATION, value)
            }
        }


    var SharedPreferences.language
        get() = getString(LANGUAGE, Language.English.name)
        set(value) {
            editMe {
                it.putString(LANGUAGE, value)
            }
        }

    var SharedPreferences.windSpeed
        get() = getString(WIND_SPEED, Units.Metric.windSpeed)
        set(value) {

            editMe {
                it.putString(WIND_SPEED, value)
            }
        }



    var SharedPreferences.location
        get() = getString(LOCATION, Location.GPS.name)
        set(value) {
            editMe {
                it.putString(LOCATION, value)
            }
        }


    var SharedPreferences.nameOfCity
        get() = getString(NAME_OF_CITY, "")
        set(value) {

            editMe {
                it.putString(NAME_OF_CITY, value)
            }
        }

}