package com.kh.mo.weatherforecast.model.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import com.kh.mo.weatherforecast.ui.home.SourceOpenHome


@Entity(tableName = "Alert_Table", primaryKeys = ["lat","lon","startTime"])
data class AlertEntity(
    @NonNull
    val lat: Double,
    @NonNull
    val lon: Double,
    val nameOfCountry:String,
    val nameOfCity:String,
    @NonNull
    val startDate: String,
    val startTime: String,
    val endDate: String,
    val endTime: String,



):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(lat)
        parcel.writeDouble(lon)
        parcel.writeString(nameOfCountry)
        parcel.writeString(nameOfCity)
        parcel.writeString(startDate)
        parcel.writeString(startTime)
        parcel.writeString(endDate)
        parcel.writeString(endTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlertEntity> {
        override fun createFromParcel(parcel: Parcel): AlertEntity {
            return AlertEntity(parcel)
        }

        override fun newArray(size: Int): Array<AlertEntity?> {
            return arrayOfNulls(size)
        }
    }
}