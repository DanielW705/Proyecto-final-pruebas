package com.example.walkdog

import ViewPagerAdapter
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.myapp.MyAdapter
import com.google.android.gms.maps.model.LatLng

class MenuActivity : AppCompatActivity() {


    private lateinit var viewPager: ViewPager

    private lateinit var ViewModel: PerroViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ViewModel = ViewModelProvider(this).get(PerroViewModel::class.java)

        viewPager = findViewById(R.id.ViewPager)


        val adapter = ViewPagerAdapter(supportFragmentManager, ViewModel)
        viewPager.adapter = adapter

//        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//
//        val btnProfile: Button = findViewById(R.id.btnProfile)
//        val btnViewDogs: Button = findViewById(R.id.btnViewDogs)
//        btnProfile.setOnClickListener {
//            navController.navigate(R.id.firstFragment)
//        }
//        btnViewDogs.setOnClickListener {
//            navController.navigate(R.id.secondFragment)
//        }
    }
}