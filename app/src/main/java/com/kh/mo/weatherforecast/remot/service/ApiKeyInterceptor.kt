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
        val customPreference = SharedPreferencesWeather.customPreference(context)
        val tempUnit =customPreference.tempUnit.toString()
        val language =customPreference.language
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("appid", BuildConfig.apiKey)
            .addQueryParameter("units", tempUnit)
            .addQueryParameter("lang", language)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
