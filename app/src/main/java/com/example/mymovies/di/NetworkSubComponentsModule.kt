package com.example.mymovies.di

import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.di.component.NetworkComponent
import dagger.Module

@ExperimentalPagingApi
@Module(
        subcomponents = [NetworkComponent::class]
)
object NetworkSubComponentsModule {
}