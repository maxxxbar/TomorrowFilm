package com.example.mymovies.di.component

import com.example.mymovies.di.MyScope
import com.example.mymovies.di.module.FavoriteFragmentModule
import com.example.mymovies.ui.favoritefragment.FavoriteFragment
import dagger.Subcomponent

@MyScope
@Subcomponent(modules = [FavoriteFragmentModule::class])
interface FavoriteFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FavoriteFragmentComponent
    }

    fun inject(fragment: FavoriteFragment)

}