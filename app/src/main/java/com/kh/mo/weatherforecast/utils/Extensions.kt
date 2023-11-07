package com.kh.mo.weatherforecast.utils

import android.content.Context
import android.location.Geocoder
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Date
import java.util.TimeZone
import java.util.Locale
import java.text.SimpleDateFormat

fun createDialog(context: Context, view: Int) {
    MaterialAlertDialogBuilder(context)
        .setView(view)
        .show()
}

fun getAddressLocation(lat: Double, lon: Double,context: Context,
                       getLocationData:(lat:Double,lon:Double,nameOfCity:String,
                                        nameOfCountry:String)->Unit
                       ) {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocation(
        lat, lon, 1
    )

    if (addresses?.isNotEmpty() == true) {
        val address = addresses[0]
        val fullAddress = address.getAddressLine(0)
        val addressData = fullAddress.split(",")
        val nameOfCity = addressData[1]
        val nameOfCountry = addressData[addressData.size-1]
        getLocationData(lat, lon, nameOfCity,nameOfCountry)

    }
}



fun Long.convertUnixTimestampToDateTime(): String {
    val dateFormat = SimpleDateFormat("hh a", Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val dateTime = Date(this * 1000L) // Convert seconds to milliseconds
    return dateFormat.format(dateTime)
}


fun Fragment.closeFragment() {
    this.findNavController().popBackStack()
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeInVisible() {
    this.visibility = View.INVISIBLE
}