package com.example.mymovies.ui.mainactivity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mymovies.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host)
        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MainActivityViewModel::class.java)
    }

    fun onClickFirst(view: View) {
        navController.navigate(R.id.firstFragment)
    }

    fun onClickSecond(view: View) {
        navController.navigate(R.id.secondFragment)
    }
}