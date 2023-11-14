package com.kh.mo.weatherforecast.utils

import android.content.Context
import android.location.Geocoder
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kh.mo.weatherforecast.R
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

private var isDialogShowing = false

fun createDialog(title:String="",message:String="",view: View?=null,
                 context: Context,sure:()->Unit,cancel:()->Unit){
    if (!isDialogShowing) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setView(view)
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                cancel()
                dialog.dismiss()

            }
            .setPositiveButton(context.getString(R.string.done)) { dialog, _ ->
                sure()
                dialog.dismiss()

            }
            .show()
    }
}