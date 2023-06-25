package com.mooddiary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mooddiary.R
import com.mooddiary.databinding.HomeBinding

class HomeFragment: Fragment() {
    private lateinit var _binding: HomeBinding
    private val _navController: NavController by lazy { initNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavBar()
    }

    private fun initNavController(): NavController {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.bottom_nav_container_view) as NavHostFragment
        return navHostFragment.navController
    }

    private fun initBottomNavBar() {
        _binding.bottomNavBar.setupWithNavController(_navController)
    }
}