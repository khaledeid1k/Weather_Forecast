package com.kh.mo.weatherforecast.ui.alert

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.AlertCountryItemBinding
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.ui.base.BaseDataDiffUtil

class AlertCountryAdapter(private val alertCountryListener: AlertCountryListener)
    : RecyclerView.Adapter<AlertCountryAdapter.AlertCountryViewHolder>() {

    private var favorites: List<FavoriteEntity> = emptyList()

//    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertCountryViewHolder {


        return AlertCountryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.alert_country_item,
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlertCountryViewHolder, position: Int) {
        val favorite = favorites[position]
        holder.bind(favorite,alertCountryListener)

//        holder.itemView.setOnClickListener {
//            selectedPosition=position
//            if (position == selectedPosition) {
//                holder.itemView.setBackgroundResource( R.color.delete_color )
//            } else {
//                android.R.color.transparent // Default color for unselected items
//            }
//            // Update the selected position
//            val previousSelectedPosition = selectedPosition
//            selectedPosition = holder.adapterPosition
//
//            // Notify the adapter about the changes
//            notifyItemChanged(previousSelectedPosition)
//            notifyItemChanged(selectedPosition)
//
//        }

    }

    override fun getItemCount()=favorites.size

    class AlertCountryViewHolder (private val binding: AlertCountryItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(favorite: FavoriteEntity, alertCountryListener: AlertCountryListener) {
            binding.favorite = favorite
            binding.listener=alertCountryListener
        }
    }

    fun setItems(newItems: List<FavoriteEntity>) {
        val diffResult = DiffUtil.calculateDiff(
            BaseDataDiffUtil(favorites, newItems,
                checkItemsTheSame=    { oldItemPosition, newItemPosition -> favorites[oldItemPosition].nameOfCity == newItems[newItemPosition].nameOfCity },
                checkContentsTheSame=  { oldItemPosition, newItemPosition -> favorites[oldItemPosition] == newItems[newItemPosition] }
            )
        )
        favorites = newItems
        diffResult.dispatchUpdatesTo(this)
    }


    interface AlertCountryListener {
        fun onClickFavourite(favorite: FavoriteEntity)
    }
}
