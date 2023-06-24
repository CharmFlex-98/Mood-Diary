package com.mooddiary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mooddiary.R
import com.mooddiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val _binding: ActivityMainBinding by lazy { initBinding() }
    private val _navController: NavController by lazy { initNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        initBottomNavigationBar()
    }

    private fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private fun initNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    private fun initBottomNavigationBar() {
        _binding.bottomNavBar.setupWithNavController(_navController)
    }
}