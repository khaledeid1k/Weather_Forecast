package com.kh.mo.weatherforecast.local.db.sharedPref

import android.content.Context
import android.content.SharedPreferences
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.ui.setting.Units
import com.kh.mo.weatherforecast.utils.Constants.FIRST_TIME
import com.kh.mo.weatherforecast.utils.Constants.LANGUAGE
import com.kh.mo.weatherforecast.utils.Constants.LAT
import com.kh.mo.weatherforecast.utils.Constants.LOCATION
import com.kh.mo.weatherforecast.utils.Constants.LON
import com.kh.mo.weatherforecast.utils.Constants.NAME_OF_CITY
import com.kh.mo.weatherforecast.utils.Constants.NOTIFICATION
import com.kh.mo.weatherforecast.utils.Constants.PREFERENCE_NAME
import com.kh.mo.weatherforecast.utils.Constants.TEMP_UNIT
import com.kh.mo.weatherforecast.utils.Constants.WIND_SPEED
 class SharedPreferencesWeather (val context: Context){

    fun customPreference(): SharedPreferences =

        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)



}