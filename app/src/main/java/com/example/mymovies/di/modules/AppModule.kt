package com.example.mymovies.di.modules

import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.utils.Extra
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@ExperimentalPagingApi
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideSortBy() = Extra.SORT_BY_POPULARITY

    @Singleton
    @Provides
    fun provideVoteCount() = Extra.VOTE_COUNT_GTE


}
