package com.example.mymovies.di.module

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.di.component.NetworkComponent
import com.example.mymovies.utils.Extra
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@ExperimentalPagingApi
@Module(
       // subcomponents = [NetworkComponent::class]
)
object AppModule {

    @Singleton
    @Provides
    fun provideSortBy() = Extra.SORT_BY_POPULARITY

    @Singleton
    @Provides
    fun provideVoteCount() = Extra.VOTE_COUNT_GTE

}
