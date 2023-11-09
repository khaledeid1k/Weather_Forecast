package com.kh.mo.weatherforecast.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.FragmentHomeBinding
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm
import com.kh.mo.weatherforecast.utils.Constants.FAVORITE_FRAGMENT
import com.kh.mo.weatherforecast.utils.closeFragment
import com.kh.mo.weatherforecast.utils.makeGone
import com.kh.mo.weatherforecast.utils.makeVisible

class HomeFragment : Fragment() {


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiViewModel()
        setUp()
        checkFromNavBarOrFragment()
        changeValueOfFirstTimeOpenApp()
        backToFavorite()
    }

    private fun intiViewModel() {
        val showProductsViewModelFactory =
            HomeViewModelFactory(
                RepoIm.getRepoImInstance
                    (
                    LocalDataImp.getLocalDataImpInstance(requireContext()),
                    RemoteDataImp
                )
            )
        homeViewModel = ViewModelProvider(
            this,
            showProductsViewModelFactory
        )[HomeViewModel::class.java]
    }


    private fun setUp() {
        binding.apply {
            viewModel = homeViewModel
            lifecycleOwner = this@HomeFragment
        }
    }

    private fun checkFromNavBarOrFragment() {
        val locationData = receiveLocationData() ?: getSavedLanAndLon()
        binding.apply {
            if (locationData.type == FAVORITE_FRAGMENT) {
                backTofavorite.makeVisible()
                tileOfHome.text = requireContext().getString(R.string.favourite)
            }
        }

        sendLocationDataToViewModel(locationData)
    }

    private fun receiveLocationData() = HomeFragmentArgs.fromBundle(requireArguments()).locationData


    private fun sendLocationDataToViewModel(locationData: LocationData) {
        homeViewModel.getCurrentUpdatedWeatherState(locationData)
    }

    private fun changeValueOfFirstTimeOpenApp() {
        homeViewModel.changeValueOfFirstTimeOpenApp(false)
    }

    private fun getSavedLanAndLon() = homeViewModel.let { LocationData(it.getLat(), it.getLon()) }

    private fun backToFavorite() {
        binding.backTofavorite.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionShowWeatherDataToFavourite()
            )
        }
    }

}





