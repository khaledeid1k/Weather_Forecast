package com.kh.mo.weatherforecast.ui.alert

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.AlertCountryItemBinding
import com.kh.mo.weatherforecast.databinding.AlertItemBinding
import com.kh.mo.weatherforecast.model.entity.AlertEntity
import com.kh.mo.weatherforecast.ui.base.BaseDataDiffUtil

class AlertAdapter(private val alertListener: AlertListener)
    : RecyclerView.Adapter<AlertAdapter.AlertAdapterViewHolder>() {

    private var alerts: List<AlertEntity> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertAdapterViewHolder {


        return AlertAdapterViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.alert_item,
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlertAdapterViewHolder, position: Int) {
        val alert = alerts[position]
        holder.bind(alert,alertListener)


    }

    override fun getItemCount()=alerts.size

  inner  class AlertAdapterViewHolder (private val binding: AlertItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(alert: AlertEntity,alertListener: AlertListener) {
            binding.alert = alert
            binding.listener=alertListener
        }
    }

    fun setItems(newItems: List<AlertEntity>) {
        val diffResult = DiffUtil.calculateDiff(
            BaseDataDiffUtil(alerts, newItems,
                checkItemsTheSame=    { oldItemPosition, newItemPosition -> alerts[oldItemPosition].nameOfCity == newItems[newItemPosition].nameOfCity },
                checkContentsTheSame=  { oldItemPosition, newItemPosition -> alerts[oldItemPosition] == newItems[newItemPosition] }
            )
        )
        alerts = newItems
        diffResult.dispatchUpdatesTo(this)
    }


    interface AlertListener {
        fun deleteAlert(alertEntity: AlertEntity)
    }
}
