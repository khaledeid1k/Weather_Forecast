package com.kh.mo.weatherforecast.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.FragmentMapBinding
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var locationData: LocationData
    private lateinit var mapViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_map, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiMapView(savedInstanceState)
        intiViewModel()
        setUp()

    }

  private fun receiveNameOfCaller() = MapFragmentArgs.fromBundle(requireArguments()).sourceOpenMap


    private fun intiMapView(savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        p0.moveCamera(CameraUpdateFactory.zoomIn())
        p0.setOnMapClickListener { showDialog(it) }
    }

    private fun intiViewModel() {
        val mapViewModelFactory =
            MapViewModelFactory(
                RepoIm.getRepoImInstance
                    (
                    LocalDataImp.getLocalDataImpInstance(requireContext()),
                    RemoteDataImp.getRemoteDataImpInstance(requireContext())
                )
            )
        mapViewModel = ViewModelProvider(
            this,
            mapViewModelFactory
        )[MapViewModel::class.java]
    }

    private fun setUp() {
        binding.apply {
            lifecycleOwner = this@MapFragment
        }
    }

    private fun createDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("You are Select")
            .setMessage(message)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Done") { dialog, _ ->
              moveToNextScreen()
                dialog.dismiss()
            }
            .show()
    }


    private fun showDialog(latLng: LatLng) {
        mapViewModel.getAddressLocation(
            latLng.latitude,
            latLng.longitude
        ) { nameOfCity, nameOfCountry ->
            intiLocationData(latLng, nameOfCity)
            createDialog("$nameOfCountry , $nameOfCity")
        }

    }

    private fun intiLocationData(latLng: LatLng, nameOfCity: String) {
        locationData = LocationData(latLng.latitude, latLng.longitude, nameOfCity)
    }

    private fun moveToNextScreen() {
        when(receiveNameOfCaller()){
           SourceOpenMap.SETTING_FRAGMENT ->{
                findNavController().navigate(
                    MapFragmentDirections.actionMapFragmentToSettings(
                        locationData
                    )
                )
            }
            SourceOpenMap.FAVORITE_FRAGMENT ->{ findNavController().navigate(
            MapFragmentDirections.actionMapFragmentToFavourite(locationData)
        )}
            SourceOpenMap.INITIAL_FRAGMENT->{
                findNavController().navigate(
                    MapFragmentDirections.actionMapFragmentToHome(
                        locationData
                    )
                )
            }
        }

    }
}
