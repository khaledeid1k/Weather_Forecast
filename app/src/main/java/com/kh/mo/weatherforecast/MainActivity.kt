package com.kh.mo.weatherforecast

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kh.mo.weatherforecast.local.LocalDataImp
import com.kh.mo.weatherforecast.remot.RemoteDataImp
import com.kh.mo.weatherforecast.repo.RepoIm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var controller: NavController
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBottomNavigationView()
        val repoImInstance = RepoIm.getRepoImInstance(
            LocalDataImp.getLocalDataImpInstance(this),
            RemoteDataImp
        )
        CoroutineScope(Dispatchers.IO).launch {
            val currentTemperature = repoImInstance?.getCurrentTemperature()
            Log.d("TAG", "onCraaaaaeate:$currentTemperature ")
        }
    }

    fun setUpBottomNavigationView() {
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        controller = navHostFragment.navController
        setupWithNavController(bottomNavigationView, controller)
    }

}