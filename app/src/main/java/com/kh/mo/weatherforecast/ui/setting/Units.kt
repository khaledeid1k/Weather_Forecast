package com.kh.mo.weatherforecast.ui.setting

enum class Units(val nameOfUnit:String,val windSpeed:String,val tempUnit:String) {
    Standard("standard","metre/sec","Kelvin") ,
    Metric("metric","metre/sec","Celsius"),
    Imperial("imperial","miles/hour","Fahrenheit")
}