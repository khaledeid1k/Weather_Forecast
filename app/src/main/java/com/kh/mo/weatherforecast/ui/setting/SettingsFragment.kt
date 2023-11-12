package com.kh.mo.weatherforecast.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kh.mo.weatherforecast.MainActivity
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.FragmentSettingsBinding
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm
import com.kh.mo.weatherforecast.ui.initial.LocationServiceChecker
import com.kh.mo.weatherforecast.ui.map.SourceOpenMap


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingViewModel: SettingsViewModel

    val TAG = "SettingsFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiViewModel()
        getValueOFTempUnit()

        getValueOFWindSpeed()
        getValueOFLanguage()
        getValueOFNotification()


        saveTempUnit()
        saveWindSpeed()
        saveLanguage()
        saveNotification()

    }

    override fun onResume() {
        super.onResume()
        saveLocationByMap()
        selectLocation()
        getValueOFLocation()
    }
    private fun intiViewModel() {
        val settingsViewModelFactory =
            SettingsViewModelFactory(
                RepoIm.getRepoImInstance
                    (
                    LocalDataImp.getLocalDataImpInstance(requireContext()),
                    RemoteDataImp.getRemoteDataImpInstance(requireContext())
                )
            )
        settingViewModel = ViewModelProvider(
            this,
            settingsViewModelFactory
        )[SettingsViewModel::class.java]

    }




    private fun navigateToMapScreen() {
        findNavController().navigate(
            SettingsFragmentDirections.actionSettingsToMapFragment(SourceOpenMap.SETTING_FRAGMENT)
        )

    }


    private fun getValueOFLanguage() {
        if (settingViewModel.getLanguage() == Language.ar.name) binding.languageArabic.isChecked =
            true else binding.languageEnglish.isChecked = true
    }

    private fun getValueOFNotification() {
        if (settingViewModel.getNotification()) binding.notificationEnable.isChecked =
            true else binding.notificationDisable.isChecked = true
    }

    private fun getValueOFLocation() {
        if (settingViewModel.getWayOfSelectLocation() == Location.GPS.name) makeGPSChecked() else makeMAPChecked()
    }

    private fun getValueOFWindSpeed() {
        val windSpeed = settingViewModel.getWindSpeed()
        if ( windSpeed== Units.Metric.windSpeed || windSpeed == Units.Standard.windSpeed) binding.windSpeedMetre.isChecked = true else binding.windSpeedMiles.isChecked = true
    }

    private fun getValueOFTempUnit() {
        when (settingViewModel.getTempUnit()) {
            Units.Standard.nameOfUnit -> binding.unitsKelvin.isChecked = true
            Units.Metric.nameOfUnit -> binding.unitsCelsius.isChecked = true
            Units.Imperial.nameOfUnit -> binding.unitsFahrenheit.isChecked = true
        }


    }



    private fun saveNotification() {
        settingViewModel.setNotification(binding.notificationEnable.isChecked)
    }


    private fun receiveLocationDataFromMap() =
        SettingsFragmentArgs.fromBundle(requireArguments()).locationData
    private fun getLocationDataGPS(callback: (LocationData) -> Unit) {
        LocationServiceChecker(
            requireContext(),
            requireActivity()
        ) { lat, lon ->
            settingViewModel.getAddressLocation(lat, lon) { nameOfCity, _ ->
                callback(LocationData(lat, lon, nameOfCity))
            }
        }
    }

    private fun makeMAPChecked() {
        binding.locationMap.isChecked = true
    }
    private fun makeGPSChecked() {
        binding.locationGps.isChecked = true
    }

    private fun saveLocationByMap() {
        receiveLocationDataFromMap()?.let {
            makeMAPChecked()
            settingViewModel.saveLocationData(it)
            settingViewModel.setLocation(Location.MAP)
        }

    }
    private fun saveLocationByGps() {
        getLocationDataGPS {
            makeGPSChecked()
            settingViewModel.saveLocationData(it)
            settingViewModel.setLocation(Location.GPS)
        }

    }




    private fun selectLocation() {
        binding.locationGps.setOnClickListener {
            saveLocationByGps()
        }

        binding.locationMap.setOnClickListener {
            navigateToMapScreen()
        }
    }


    private fun saveWindSpeed() {
        binding.windSpeedMiles.setOnClickListener {
            binding.unitsFahrenheit.isChecked=true

            settingViewModel.setWindSpeed(Units.Imperial)
         }
        binding.windSpeedMetre.setOnClickListener {
            binding.unitsKelvin.isChecked=true
            settingViewModel.setWindSpeed(Units.Metric)   }
    }
    private fun saveTempUnit() {
            binding.unitsKelvin.setOnClickListener {
                binding.windSpeedMetre.isChecked = true
                settingViewModel.setTempUnit(Units.Standard)}
            binding.unitsCelsius.setOnClickListener {
                binding.windSpeedMetre.isChecked = true
                settingViewModel.setTempUnit(Units.Metric) }
            binding.unitsFahrenheit.setOnClickListener {
                binding.windSpeedMiles .isChecked = true
                settingViewModel.setTempUnit(Units.Imperial) }

        }



    private fun saveLanguage() {
        binding.languageArabic.setOnClickListener {
            settingViewModel.setLanguage(Language.ar)
            changeLanguage(Language.ar.name)
        }
        binding.languageEnglish.setOnClickListener {
            settingViewModel.setLanguage(Language.en)
            changeLanguage(Language.en.name)
        }
    }
    private fun changeLanguage(language: String) {
        settingViewModel.changeLanguageApp(language)
        refreshActivity()
    }
    private fun refreshActivity(){
        requireActivity().finish()
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
    }




}