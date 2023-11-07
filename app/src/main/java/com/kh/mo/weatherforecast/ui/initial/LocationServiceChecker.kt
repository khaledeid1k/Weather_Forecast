package com.kh.mo.weatherforecast.ui.initial

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import com.google.android.gms.location.*


class LocationServiceChecker(
    private val context: Context,
    private val activity: Activity,
    private val getLocationData: (lat: Double, lon: Double) -> Unit
) {
    private val locationPermission = LocationPermission(context, activity)
    private val fusedLocationProviderClient: FusedLocationProviderClient
            by lazy { LocationServices.getFusedLocationProviderClient(activity) }


    private val locationCallBack: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val lastLocation = locationResult.lastLocation

            getLocationData(lastLocation.latitude, lastLocation.longitude)


        }
    }

    init {
        getLocation()
    }

    private fun getLocation() {
        if (locationPermission.checkLocationPermission()) {
            if (isLocationEnable()) {
                requestNewLocation()
            } else {
                Toast.makeText(context, "Please , Turn On Location", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        } else {
            locationPermission.requestLocationPermission()
        }
    }


    private fun isLocationEnable(): Boolean {
        val localeManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return localeManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                ||
                localeManager.isProviderEnabled(
                    LocationManager.NETWORK_PROVIDER
                )

    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocation() {
        val locationRequest = LocationRequest().also {
            it.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            it.interval = 600000
//            it.fastestInterval = 600000
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallBack, Looper.myLooper()
        )
    }


}