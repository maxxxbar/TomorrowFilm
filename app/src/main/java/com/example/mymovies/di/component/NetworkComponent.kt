package com.example.mymovies.di.component

import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.App
import com.example.mymovies.datasource.MoviePagingSource
import com.example.mymovies.datasource.MovieRemoteMediator
import com.example.mymovies.di.NetworkScope
import com.example.mymovies.di.module.NetworkModule
import dagger.Subcomponent

@NetworkScope
@ExperimentalPagingApi
@Subcomponent(
        modules = [NetworkModule::class]
)
interface NetworkComponent {

//    @Subcomponent.Factory
//    interface Factory {
//        fun network(): NetworkComponent
//    }

    @Subcomponent.Builder
    interface Builder {
        fun networkModule(module: NetworkModule): Builder
        fun build(): NetworkComponent
    }

    fun inject(app: App)
    fun inject(remoteMediator: MovieRemoteMediator)
    fun inject(moviePagingSource: MoviePagingSource)

}