package com.kh.mo.weatherforecast.remot.service

import android.content.Context
import android.util.Log
import com.kh.mo.weatherforecast.BuildConfig
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.language
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.tempUnit
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val tempUnit = SharedPreferencesWeather.customPreference(context).tempUnit.toString()
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("appid", BuildConfig.apiKey)
            .addQueryParameter("units", tempUnit)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
