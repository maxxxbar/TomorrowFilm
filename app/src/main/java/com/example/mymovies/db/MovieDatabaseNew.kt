package com.example.mymovies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.model.RemoteKeys

@Database(
        entities = [DiscoverMovieResultsItem::class, RemoteKeys::class],
        version = 1,
        exportSchema = false
)
abstract class MovieDatabaseNew : RoomDatabase() {
    abstract fun movieDao(): Dao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabaseNew? = null

        fun getInstance(context: Context): MovieDatabaseNew =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabaseNew::class.java, "qweqwe.db")
                        .fallbackToDestructiveMigration()
                        .build()
    }
}