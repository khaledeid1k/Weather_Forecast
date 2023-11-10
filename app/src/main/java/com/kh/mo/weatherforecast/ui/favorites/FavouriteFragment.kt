package com.kh.mo.weatherforecast.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.FragmentFavouriteBinding
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.ui.home.SourceOpenHome
import com.kh.mo.weatherforecast.ui.map.SourceOpenMap
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm
import kotlinx.coroutines.launch


class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var favouriteViewModel: FavouriteViewModel
    private lateinit var adapter: FavouriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_favourite, container, false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiViewModel()
        setUp()
        addAdapter()
        clickFavorite()
    }

    private fun setUp(){
        binding.apply {
            lifecycleOwner=this@FavouriteFragment
        }

    }

    private fun addAdapter() {
         adapter = FavouriteAdapter(favouriteViewModel)
        binding.recycleFavorites.adapter = adapter
    }

    private fun getFavorites() {
        lifecycleScope.launch {
            favouriteViewModel.favorites.collect{
                adapter.setItems(it)
            }
        }
    }



    private fun intiViewModel() {
        val favoriteViewModelFactory =
            FavoriteViewModelFactory(
                RepoIm.getRepoImInstance
                    (
                    LocalDataImp.getLocalDataImpInstance(requireContext()),
                    RemoteDataImp.getRemoteDataImpInstance(requireContext())
                )
            )
        favouriteViewModel = ViewModelProvider(
            this,
            favoriteViewModelFactory
        )[FavouriteViewModel::class.java]
    }

    private fun receiveLocationData() {
         FavouriteFragmentArgs.fromBundle(requireArguments()).locationData
            ?.let {
                getWeatherStateOfLocation(it)
            }


    }

    private fun getWeatherStateOfLocation(locationData : LocationData){
        favouriteViewModel.getWeatherStateOfLocation(locationData)
    }

    private fun addFavoriteLocation(){
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(FavouriteFragmentDirections.actionFavouriteToMapFragment(
                SourceOpenMap.FAVORITE_FRAGMENT))
        }
    }

    override fun onResume() {
        super.onResume()
        getFavorites()
        addFavoriteLocation()
        receiveLocationData()
    }

   private fun clickFavorite(){
        favouriteViewModel.favoritesEvent.observe(viewLifecycleOwner){
            findNavController().navigate(FavouriteFragmentDirections.actionFavouriteToShowWeatherData (LocationData(it.lan,it.lon, type = SourceOpenHome.FAVORITE_FRAGMENT)))

        }
    }
}