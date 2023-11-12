package com.kh.mo.weatherforecast.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.ItemStateOfDayBinding
import com.kh.mo.weatherforecast.model.ui.WeatherHourData
import com.kh.mo.weatherforecast.ui.base.BaseDataDiffUtil

class WeatherHourAdapter
    : RecyclerView.Adapter<WeatherHourAdapter.WeatherHourViewHolder>() {

    private var weatherHoursData: List<WeatherHourData> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHourViewHolder {


        return WeatherHourViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_state_of_day,
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherHourViewHolder, position: Int) {
        val weatherHoursData = weatherHoursData[position]
        holder.bind(weatherHoursData)
    }

    override fun getItemCount()=weatherHoursData.size

    class WeatherHourViewHolder (private val binding: ItemStateOfDayBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(weatherHourData: WeatherHourData) { binding.weatherHourData = weatherHourData }
    }

    fun setItems(newItems: List<WeatherHourData>) {
        val diffResult = DiffUtil.calculateDiff(
            BaseDataDiffUtil(weatherHoursData, newItems,
                checkItemsTheSame=    { oldItemPosition, newItemPosition -> weatherHoursData[oldItemPosition].hour == newItems[newItemPosition].hour },
                checkContentsTheSame=  { oldItemPosition, newItemPosition -> weatherHoursData[oldItemPosition] == newItems[newItemPosition] }
            )
        )
        weatherHoursData = newItems
        diffResult.dispatchUpdatesTo(this)
    }








}
