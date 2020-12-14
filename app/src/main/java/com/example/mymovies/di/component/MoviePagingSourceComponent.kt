package com.example.mymovies.di.component

import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.datasource.MoviePagingSource
import com.example.mymovies.di.MyScope
import com.example.mymovies.di.NetworkSubComponentsModule
import com.example.mymovies.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton


@ExperimentalPagingApi
@MyScope
@Component(
        modules = [NetworkSubComponentsModule::class]
)
interface MoviePagingSourceComponent {

    fun networkBuilder(): NetworkComponent.Builder

}
