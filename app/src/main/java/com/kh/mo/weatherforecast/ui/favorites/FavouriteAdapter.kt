package com.kh.mo.weatherforecast.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.FavoriteItemBinding
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.ui.base.BaseDataDiffUtil

class FavouriteAdapter(private val favouriteListener: FavouriteListener)
    : RecyclerView.Adapter<FavouriteAdapter.FavoriteViewHolder>() {

    private var favorites: List<FavoriteEntity> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {


        return FavoriteViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.favorite_item,
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorites = favorites[position]
        holder.bind(favorites,favouriteListener)
    }

    override fun getItemCount()=favorites.size

    class FavoriteViewHolder (private val binding: FavoriteItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(favorite: FavoriteEntity,favouriteListener: FavouriteListener) {

            binding.favorite = favorite
            binding.listener=favouriteListener
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






    interface FavouriteListener {
        fun onClickFavourite(favorite: FavoriteEntity)
        fun deleteFavourite(favorite: FavoriteEntity)
    }
}
