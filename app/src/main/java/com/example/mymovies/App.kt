package com.example.mymovies

import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

@ExperimentalPagingApi
class App() : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory()
                .create(applicationContext)
    }

}
