package com.kh.mo.weatherforecast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kh.mo.weatherforecast.databinding.ActivityMainBinding
import com.kh.mo.weatherforecast.utils.createDialog

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
       // requestInitionSetUp()

    }
    private fun requestInitionSetUp(){
        createDialog(this,R.layout.inition_set_up)
    }

    private fun setUpBottomNavigationView() {
       bottomNavigationView = findViewById(R.id.bottom_navigation)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        controller = navHostFragment.navController
        setupWithNavController(bottomNavigationView, controller)

    }





}