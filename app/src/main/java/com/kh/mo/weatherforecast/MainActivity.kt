package com.kh.mo.weatherforecast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kh.mo.weatherforecast.databinding.ActivityMainBinding
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.isFirstTimeOpenApp
import com.kh.mo.weatherforecast.utils.makeGone
import com.kh.mo.weatherforecast.utils.makeVisible

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = DataBindingUtil.setContentView(
            this,R.layout.activity_main
        )
        setUpBottomNavigationView()
        disappearAndShowBottomNavigation()
        skipInitFragment()


    }


    private fun setUpBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        controller = navHostFragment.navController
        setupWithNavController(bottomNavigationView, controller)

    }

    private fun disappearAndShowBottomNavigation() {
        controller.addOnDestinationChangedListener { _: NavController?,
                                                     destination: NavDestination,
                                                     _: Bundle? ->
            val destinationId = destination.id
            if (destinationId == R.id.initialFragment
                ||
                destinationId == R.id.mapFragment
            ) {
                binding.bottomNavigation.makeGone()
            }else{   binding.bottomNavigation.makeVisible()}
        }


    }


    private fun checkIsFirstTimeOpenApp() =
        SharedPreferencesWeather.customPreference(this).isFirstTimeOpenApp

    private fun skipInitFragment() {
        if (!checkIsFirstTimeOpenApp()) {
            controller.popBackStack(R.id.initialFragment,true)
            controller.navigate(R.id.home)
        }
    }
}