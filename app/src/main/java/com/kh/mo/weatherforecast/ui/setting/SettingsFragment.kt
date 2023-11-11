package com.kh.mo.weatherforecast.ui.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.FragmentSettingsBinding
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.ui.map.SourceOpenMap
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm
import com.kh.mo.weatherforecast.ui.initial.LocationServiceChecker


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
        correspondWindSpeedAndUnitTemp()


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
        if (settingViewModel.getLanguage() == Language.Arabic.name) binding.languageArabic.isChecked =
            true else binding.languageEnglish.isChecked = true
    }

    private fun getValueOFNotification() {
        if (settingViewModel.getNotification()) binding.notificationEnable.isChecked =
            true else binding.notificationDisable.isChecked = true
    }

    private fun getValueOFLocation() {
        if (settingViewModel.getLocation() == Location.GPS.name) makeGPSChecked() else makeMAPChecked()
    }

    private fun getValueOFWindSpeed() {
        val windSpeed = settingViewModel.getWindSpeed()
        Log.d(TAG, "getValueOFWindSpeed: $windSpeed")
        if (settingViewModel.getWindSpeed() == Units.Metric.windSpeed || settingViewModel.getWindSpeed() == Units.Standard.windSpeed) binding.windSpeedMeter.isChecked =
            true else binding.windSpeedMiles.isChecked = true
    }

    private fun getValueOFTempUnit() {
        val tempUnit = settingViewModel.getTempUnit()
        Log.d(TAG, "getValueOFTempUnit: $tempUnit")
        when (settingViewModel.getTempUnit()) {
            Units.Standard.nameOfUnit -> binding.unitsKelvin.isChecked = true
            Units.Metric.nameOfUnit -> binding.unitsCelsius.isChecked = true
            Units.Imperial.nameOfUnit -> binding.unitsFahrenheit.isChecked = true
        }


    }


    private fun saveLanguage() {
        if (binding.radioGroupLanguage.findViewById<RadioButton>(
                binding.radioGroupLanguage.checkedRadioButtonId
            ).text
            ==
            Language.Arabic.name
        ) {
            settingViewModel.setLanguage(Language.Arabic)
        } else {
            settingViewModel.setLanguage(Language.English)
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

    private fun correspondWindSpeedAndUnitTemp() {
        binding.radioGroupWindSpeed.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.wind_speed_miles -> {
                    binding.unitsFahrenheit.isChecked = true
                }
                R.id.wind_speed_meter -> {
                    binding.unitsKelvin.isChecked = true

                }
            }
        }
        binding.radioGroupUnits.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.units_celsius,
                R.id.units_kelvin -> {
                    binding.windSpeedMeter.isChecked = true
                }
                R.id.units_fahrenheit -> {
                    binding.windSpeedMiles.isChecked = true

                }
            }
        }
    }

    private fun saveWindSpeed() {
        when (binding.radioGroupWindSpeed.findViewById<RadioButton>(
            binding.radioGroupWindSpeed.checkedRadioButtonId
        ).text) {
            Units.Metric.windSpeed,
            Units.Standard.windSpeed -> settingViewModel.setWindSpeed(Units.Metric)
            Units.Imperial.windSpeed -> settingViewModel.setWindSpeed(Units.Imperial)
        }

    }

    private fun saveTempUnit() {
        val checkedRadioButtonId = binding.radioGroupUnits.checkedRadioButtonId
        if (checkedRadioButtonId != -1) {
            when (binding.radioGroupUnits.findViewById<RadioButton>(checkedRadioButtonId).text) {
                Units.Metric.tempUnit -> settingViewModel.setTempUnit(Units.Metric)
                Units.Imperial.tempUnit -> settingViewModel.setTempUnit(Units.Imperial)
                Units.Standard.tempUnit -> settingViewModel.setTempUnit(Units.Standard)
            }

        }


    }


    override fun onStop() {
        super.onStop()
        saveTempUnit()
        saveWindSpeed()
        saveLanguage()
        saveNotification()
    }


}