package com.example.mymovies.di.module

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.ui.favoritefragment.FavoriteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FavoriteFragmentModule {

    @ExperimentalPagingApi
    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindViewModel(viewModel: FavoriteViewModel): ViewModel

}