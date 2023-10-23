package com.cezila.ksafe.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cezila.ksafe.R
import com.cezila.ksafe.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        navController.addOnDestinationChangedListener(this)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment,
            R.id.analyzePasswordFragment,
            R.id.generatePasswordFragment -> {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}