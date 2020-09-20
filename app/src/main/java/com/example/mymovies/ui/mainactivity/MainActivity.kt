package com.example.mymovies.ui.mainactivity

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mymovies.R
import com.example.mymovies.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.yariksoffice.connectivityplayground.connectivity.base.ConnectivityProvider
import dev.chrisbanes.insetter.applySystemWindowInsetsToMargin
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import dev.chrisbanes.insetter.setEdgeToEdgeSystemUiFlags

class MainActivity : AppCompatActivity(), ConnectivityProvider.ConnectivityStateListener {
    private val TAG = javaClass.simpleName
    private val provider: ConnectivityProvider by lazy { ConnectivityProvider.createProvider(this) }
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host)
        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MainActivityViewModel::class.java)


        setMyInsets()
    }

    private fun setMyInsets() {
        val view = binding.root.rootView
        val fragmentView = binding.root.findViewById<View>(R.id.nav_host)
/*
        fragmentView.applySystemWindowInsetsToMargin(top = true)
*/
        view.setEdgeToEdgeSystemUiFlags(true)
/*
        binding.bottomNavigation.applySystemWindowInsetsToMargin(bottom = true)
*/
/*        val view = binding.root.rootView
        view.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        val fragment: View = binding.root.findViewById(R.id.nav_host)
        view.setOnApplyWindowInsetsListener { v, insets ->
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                Log.d(TAG, "${insets.stableInsets} ")
            }
            return@setOnApplyWindowInsetsListener insets }

        ViewCompat.setOnApplyWindowInsetsListener(fragment) { v, insets ->
            v.setPadding(insets.stableInsetLeft, insets.stableInsetTop, insets.stableInsetRight, 0)
            return@setOnApplyWindowInsetsListener insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigation) { v, insets ->
            v.updatePadding(bottom = 132)
            return@setOnApplyWindowInsetsListener insets
        }*/
    }

    override fun onStart() {
        super.onStart()
        provider.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        provider.removeListener(this)
    }

    override fun onStateChange(state: ConnectivityProvider.NetworkState) {
        val hasInternet = state.hasInternet()
        if (!hasInternet) {
            Snackbar.make(binding.root.rootView, "Нет интернета", Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    private fun ConnectivityProvider.NetworkState.hasInternet(): Boolean {
        return (this as? ConnectivityProvider.NetworkState.ConnectedState)?.hasInternet == true
    }

    fun onClickFirst(view: View) {
        navController.navigate(R.id.firstFragment)
    }

    fun onClickSecond(view: View) {
        navController.navigate(R.id.secondFragment)
    }

}