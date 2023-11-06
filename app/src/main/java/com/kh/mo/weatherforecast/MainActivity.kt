package com.kh.mo.weatherforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.materialswitch.MaterialSwitch
import com.kh.mo.weatherforecast.databinding.ActivityMainBinding
import com.kh.mo.weatherforecast.local.db.SharedPreferencesWeather
import com.kh.mo.weatherforecast.local.db.SharedPreferencesWeather.isFirstTimeOpenApp
import com.kh.mo.weatherforecast.utils.createDialog
import com.kh.mo.weatherforecast.utils.makeGone
import com.kh.mo.weatherforecast.utils.makeVisible

class MainActivity : AppCompatActivity() {
    private lateinit var   binding : ActivityMainBinding
    private lateinit var controller: NavController
    private  lateinit var bottomNavigationView: BottomNavigationView
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

    private  fun checkIsFirstTimeOpenApp()=SharedPreferencesWeather.customPreference(this).isFirstTimeOpenApp

    private fun skipInitFragment(){
        if(!checkIsFirstTimeOpenApp()){
            controller.navigate(R.id.home)
        }
    }

    private fun disappearAndShowBottomNavigation() {
        controller.addOnDestinationChangedListener { _: NavController?,
                                                     destination: NavDestination,
                                                     _: Bundle? ->
            val destinationId = destination.id
            if (destinationId == R.id.initialFragment
            ) {
                binding.bottomNavigation.makeGone()
            }
        }
    }



}