package com.kh.mo.weatherforecast.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.FragmentHomeBinding
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm
import java.text.SimpleDateFormat
import java.util.*

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
            lifecycleOwner = this@HomeFragment
            viewModel = homeViewModel
        }
    }



    private fun getLocationData() {
        LocationPermission(requireContext(), requireActivity()) { lat, lon, address ->
            sendLatAndLonToViewModel(lat, lon,address)
        }.getLocation()
    }



    private fun sendLatAndLonToViewModel(lat: Double, lon: Double,address:String) {
        homeViewModel.apply {
            locationDataChange.postValue(LocationData(lat, lon,address))
            getWeatherState()
        }

    }

    override fun onResume() {
        super.onResume()
        getLocationData()
    }

}