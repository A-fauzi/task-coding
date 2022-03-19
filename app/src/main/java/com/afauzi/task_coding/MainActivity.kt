package com.afauzi.task_coding

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.afauzi.task_coding.consume_api.MainFragment
import com.afauzi.task_coding.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment_main)
        setupSmoothBottomMenu()
    }

    private fun setupSmoothBottomMenu() {

        val popUpMenu = PopupMenu(this, null)
        popUpMenu.inflate(R.menu.menu_bottom)
        val menu = popUpMenu.menu

        binding.bottomBar.setupWithNavController(menu, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}