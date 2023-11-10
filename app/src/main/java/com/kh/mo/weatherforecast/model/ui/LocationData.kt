package com.kh.mo.weatherforecast.model.ui

import android.os.Parcel
import android.os.Parcelable
import com.kh.mo.weatherforecast.ui.home.SourceOpenHome


data class LocationData(
    val lat: Double=0.0, val lon: Double=0.0,
    val nameOfCity: String? ="",
    val nameOfCountry:String?="",
    val type: SourceOpenHome?= SourceOpenHome.INITIAL_FRAGMENT,
)
    :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),

    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(lat)
        parcel.writeDouble(lon)
        parcel.writeString(nameOfCity)
        parcel.writeString(nameOfCountry)
    }

    override fun describeContents(): Int {
        return 0
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