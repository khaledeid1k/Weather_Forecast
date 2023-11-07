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