package com.kh.mo.weatherforecast.model.ui

import android.os.Parcel
import android.os.Parcelable


data class LocationData(val lat: Double, val lon: Double,val address:String?)
    :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }


    override fun writeToParcel(p0: Parcel, p1: Int) {
    }

    companion object CREATOR : Parcelable.Creator<LocationData> {
        override fun createFromParcel(parcel: Parcel): LocationData {
            return LocationData(parcel)
        }

        override fun newArray(size: Int): Array<LocationData?> {
            return arrayOfNulls(size)
        }
    }
}
