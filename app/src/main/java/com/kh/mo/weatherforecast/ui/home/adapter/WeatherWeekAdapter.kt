package com.kh.mo.weatherforecast.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.ItemWeatherWeekBinding
import com.kh.mo.weatherforecast.model.ui.WeatherWeekData
import com.kh.mo.weatherforecast.ui.base.BaseDataDiffUtil

class WeatherWeekAdapter
    : RecyclerView.Adapter<WeatherWeekAdapter.WeatherWeekViewHolder>() {

    private var weatherWeeksData: List<WeatherWeekData> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherWeekViewHolder {


        return WeatherWeekViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_weather_week,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherWeekViewHolder, position: Int) {
        val weatherWeeksData = weatherWeeksData[position]
        holder.bind(weatherWeeksData)
    }

    override fun getItemCount() = weatherWeeksData.size

    class WeatherWeekViewHolder(private val binding: ItemWeatherWeekBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherWeekData: WeatherWeekData) {
            binding.weatherWeekData = weatherWeekData
        }
    }

    fun setItems(newItems: List<WeatherWeekData>) {
        val diffResult = DiffUtil.calculateDiff(
            BaseDataDiffUtil(weatherWeeksData, newItems,
                checkItemsTheSame=    { oldItemPosition, newItemPosition -> weatherWeeksData[oldItemPosition].nameOfDay == newItems[newItemPosition].nameOfDay },
                checkContentsTheSame=  { oldItemPosition, newItemPosition -> weatherWeeksData[oldItemPosition] == newItems[newItemPosition] }
            )
        )
        weatherWeeksData = newItems
        diffResult.dispatchUpdatesTo(this)
    }





}