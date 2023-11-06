package com.kh.mo.weatherforecast.ui.initial

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.databinding.FragmentInitialBinding
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm
import com.kh.mo.weatherforecast.ui.home.HomeViewModel
import com.kh.mo.weatherforecast.ui.home.HomeViewModelFactory
import com.kh.mo.weatherforecast.utils.Constants.GPS
import com.kh.mo.weatherforecast.utils.Constants.MAP


class InitialFragment : Fragment() {

    private lateinit var binding: FragmentInitialBinding
    private lateinit var initialViewModel: InitialViewModel

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
       // changeValueOfFirstTimeOpenApp()
        checkWayOfSelectLocation()
        changeNotificationValue()
        submit()

    }

    private fun checkWayOfSelectLocation() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.map -> {
                    initialViewModel.changeWayOfSelectLocationValue(MAP)
                }
                R.id.gps -> {
                    initialViewModel.changeWayOfSelectLocationValue(GPS)
                }
            }
        }
    }
    private fun changeValueOfFirstTimeOpenApp() {
       initialViewModel.changeValueOfFirstTimeOpenApp(false)
    }
    private fun changeNotificationValue() {
        binding.materialSwitch.setOnCheckedChangeListener { _, isChecked ->
            initialViewModel.changeNotificationValue(isChecked)
        }

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
            viewModel  = initialViewModel
        }
    }

    private fun submit(){
        binding.submit.setOnClickListener {

            var uri = Uri.parse("geo:0,0?q=")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
            }
        }
    }
}