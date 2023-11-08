package com.kh.mo.weatherforecast.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.FavoriteItemBinding
import com.kh.mo.weatherforecast.model.ui.Favorite

class FavouriteAdapter(private val favouriteListener: FavouriteListener)
    : RecyclerView.Adapter<FavouriteAdapter.FavoriteViewHolder>() {

    private var favorites: List<Favorite> = emptyList()


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
        fun bind(favorite: Favorite,favouriteListener: FavouriteListener) {

            binding.favorite = favorite
            binding.listener=favouriteListener
        }
    }

    fun setItems(newItems: List<Favorite>) {
        val diffResult = DiffUtil.calculateDiff(FavoriteDataDiffUtil(favorites, newItems))
        favorites = newItems
        diffResult.dispatchUpdatesTo(this)
    }


    class FavoriteDataDiffUtil(
        private val oldList: List<Favorite>, private val newList: List<Favorite>
    ) : DiffUtil.Callback(){


        override fun getOldListSize()=oldList.size


        override fun getNewListSize()=newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].nameOfCity == newList[newItemPosition].nameOfCity

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]

        }

    }




    interface FavouriteListener {
        fun onClickFavourite(favorite: Favorite)
        fun deleteFavourite(favorite: Favorite)
    }
}
