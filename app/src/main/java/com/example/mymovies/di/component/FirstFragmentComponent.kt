package com.example.mymovies.di.component

import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.di.module.FirstFragmentModule
import com.example.mymovies.ui.firstfragment.FirstFragment
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [FirstFragmentModule::class])
interface FirstFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FirstFragmentComponent
    }

    @ExperimentalPagingApi
    fun inject(fragment: FirstFragment)
}