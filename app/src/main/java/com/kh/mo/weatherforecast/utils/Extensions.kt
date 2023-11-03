package com.kh.mo.weatherforecast.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun createDialog(context: Context, view: Int) {
    MaterialAlertDialogBuilder(context)
        .setView(view)
        .show()
}