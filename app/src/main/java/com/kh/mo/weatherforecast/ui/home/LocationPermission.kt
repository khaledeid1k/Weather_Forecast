package com.kh.mo.weatherforecast.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.kh.mo.weatherforecast.utils.Constants
import java.util.*

class LocationPermission(
    private val context: Context,
    private val activity: Activity,
    private val getLocationData : (lat:Double,lon:Double,address:String)->Unit
) {
    private val fusedLocationProviderClient: FusedLocationProviderClient
            by lazy { LocationServices.getFusedLocationProviderClient(activity) }




     fun getLocation() {
        if (checkLocationPermission()) {
            if (isLocationEnable()) {
                requestNewLocation()
            } else {
                Toast.makeText(context, "Please , Turn On Location", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        } else {
            requestLocationPermission()
        }
    }


    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

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

    private val locationCallBack: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val lastLocation = locationResult.lastLocation
            Log.d("lastLocation", "requestNewLocation:$lastLocation")

            getAddressLocation(lastLocation.latitude, lastLocation.longitude)

        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocation() {
        val locationRequest = LocationRequest().also {
            it.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            it.interval =60000
            it.fastestInterval = 0
        }
        Log.d("TAG", "requestNewLocation:$fusedLocationProviderClient ")
        Log.d("TAG", "requestNewLocation:$locationRequest")
        Log.d("locationCallBack", "requestNewLocation:$locationCallBack")
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallBack, Looper.myLooper()
        )
    }


    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), Constants.PERMISSION_ID
        )
    }

    fun getAddressLocation(lat: Double, lon: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(
            lat, lon, 1
        )

        if (addresses?.isNotEmpty() == true) {
            val address = addresses[0]
            val fullAddress = address.getAddressLine(0)
            val addressData = fullAddress.split(",")
            val nameOfCity = addressData[1]
            getLocationData(lat, lon, nameOfCity)

        }
    }

}