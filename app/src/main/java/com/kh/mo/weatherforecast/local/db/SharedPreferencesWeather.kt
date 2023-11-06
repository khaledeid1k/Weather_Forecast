package com.kh.mo.weatherforecast.local.db

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.kh.mo.weatherforecast.utils.Constants.FIRST_TIME
import com.kh.mo.weatherforecast.utils.Constants.GPS
import com.kh.mo.weatherforecast.utils.Constants.NOTIFICATION
import com.kh.mo.weatherforecast.utils.Constants.PREFERENCE_NAME
import com.kh.mo.weatherforecast.utils.Constants.WAY_LOCATION

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

    var SharedPreferences.isNotificationAvailable
        get() = getBoolean(NOTIFICATION, true)
        set(value) {
            editMe {
                it.putBoolean(NOTIFICATION, value)
            }
        }

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
}