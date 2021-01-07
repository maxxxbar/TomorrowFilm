package com.example.mymovies.di.modules

import android.content.Context
import androidx.room.Room
import com.example.mymovies.db.MovieDatabaseNew
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDB(context: Context): MovieDatabaseNew =
            Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabaseNew::class.java, "movies.db")
                    .fallbackToDestructiveMigration()
                    .build()
}