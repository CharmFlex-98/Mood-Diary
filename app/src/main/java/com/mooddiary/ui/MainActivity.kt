package com.mooddiary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mooddiary.R
import com.mooddiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val _binding: ActivityMainBinding by lazy { initBinding() }
    private val _navController: NavController by lazy { initNavController() }
    private val _appBarConfig: AppBarConfiguration by lazy { initAppBarConfig() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        initActionBar()
        initDrawer()
    }

    private fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    private fun initNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.drawer_container_view) as NavHostFragment
        return navHostFragment.navController
    }
    private fun initActionBar() {
        val toolbar = _binding.drawerMainContent.toolbar
        toolbar.setupWithNavController(_navController, _appBarConfig)
    }

    private fun initAppBarConfig(): AppBarConfiguration {
        // App bar config related to drawer
        return AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.settingFragment),
            _binding.drawerLayout
        )
    }


    private fun initDrawer() {
        _binding.drawerNavigationView.setupWithNavController(_navController)
    }
}