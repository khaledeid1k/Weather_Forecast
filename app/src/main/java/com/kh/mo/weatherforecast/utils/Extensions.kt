package com.kh.mo.weatherforecast.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
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



fun Long.convertUnixTimestampToDateTime(): String {
    val dateFormat = SimpleDateFormat("hh a", Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val dateTime = Date(this * 1000L) // Convert seconds to milliseconds
    return dateFormat.format(dateTime)
}