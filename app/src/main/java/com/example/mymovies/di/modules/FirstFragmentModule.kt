package com.example.mymovies.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.di.factories.ViewModelBuilder
import com.example.mymovies.di.factories.ViewModelKey
import com.example.mymovies.ui.firstfragment.FirstFragment
import com.example.mymovies.ui.firstfragment.FirstFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FirstFragmentModule {

    @ExperimentalPagingApi
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun firstFragment(): FirstFragment

    @ExperimentalPagingApi
    @Binds
    @IntoMap
    @ViewModelKey(FirstFragmentViewModel::class)
    abstract fun bindViewModel(viewModel: FirstFragmentViewModel): ViewModel
}