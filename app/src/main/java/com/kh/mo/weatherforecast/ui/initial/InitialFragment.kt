package com.kh.mo.weatherforecast.ui.initial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.FragmentInitialBinding
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm
import com.kh.mo.weatherforecast.utils.Constants.GPS
import com.kh.mo.weatherforecast.utils.Constants.INITIAL_FRAGMENT
import com.kh.mo.weatherforecast.utils.Constants.MAP


class InitialFragment : Fragment() {

    private lateinit var binding: FragmentInitialBinding
    private lateinit var initialViewModel: InitialViewModel
    private var wayOfSelectLocation = GPS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_initial, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiViewModel()
        setUp()
        getValueOfWayOfSelectLocation()
        submit()

    }

    private fun intiViewModel() {
        val initialViewModelFactory =
            InitialViewModelFactory(
                RepoIm.getRepoImInstance
                    (
                    LocalDataImp.getLocalDataImpInstance(requireContext()),
                    RemoteDataImp
                )
            )
        initialViewModel = ViewModelProvider(
            this,
            initialViewModelFactory
        )[InitialViewModel::class.java]
    }

    private fun setUp() {
        binding.apply {
            lifecycleOwner = this@InitialFragment
            viewModel = initialViewModel
        }
    }


    private fun getValueOfWayOfSelectLocation() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.map -> {
                    wayOfSelectLocation = MAP

                }
                R.id.gps -> {
                    wayOfSelectLocation = GPS

                }
            }
        }
    }


    private fun changeNotificationValue() {
        initialViewModel.changeNotificationValue(binding.materialSwitch.isChecked)
    }

    private fun changeWayOfSelectLocation() {
        initialViewModel.changeWayOfSelectLocationValue(wayOfSelectLocation)
    }


    private fun submit() {
        binding.submit.setOnClickListener {
            changeWayOfSelectLocation()
            changeNotificationValue()
            checkWayOfSelectLocation()

        }
    }

    private fun checkWayOfSelectLocation() {
        if (wayOfSelectLocation == MAP) moveToMapScreen() else moveToHome()

    }

    private fun moveToMapScreen() {
        findNavController().navigate(InitialFragmentDirections.actionInitialFragmentToMapFragment(INITIAL_FRAGMENT))
    }



    private fun getLocationData(callback: (LocationData) -> Unit) {
        LocationServiceChecker(
            requireContext(),
            requireActivity()
        ) { lat, lon ->
            initialViewModel.getAddressLocation(lat, lon) { nameOfCity, _ ->
                callback(LocationData(lat, lon, nameOfCity))
            }
        }
    }
    private fun navigateToHome(locationData: LocationData) {
        val action = InitialFragmentDirections.actionInitialFragmentToHome(locationData)
        findNavController().navigate(action)
    }
    private fun moveToHome() {
        getLocationData { locationData ->
            navigateToHome(locationData)
        }
    }
}

