package com.example.mymovies.di.module

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.ui.firstfragment.FirstFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FirstFragmentModule {

    @ExperimentalPagingApi
    @Binds
    @IntoMap
    @ViewModelKey(FirstFragmentViewModel::class)
    abstract fun bindViewModel(viewModel: FirstFragmentViewModel): ViewModel
}