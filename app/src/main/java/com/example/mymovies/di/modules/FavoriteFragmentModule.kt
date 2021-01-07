package com.example.mymovies.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.di.factories.ViewModelBuilder
import com.example.mymovies.di.factories.ViewModelKey
import com.example.mymovies.ui.favoritefragment.FavoriteFragment
import com.example.mymovies.ui.favoritefragment.FavoriteViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FavoriteFragmentModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun favoriteFragment(): FavoriteFragment

    @ExperimentalPagingApi
    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindViewModel(viewModel: FavoriteViewModel): ViewModel

}