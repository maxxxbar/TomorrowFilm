package ws.worldshine.tomorrowfilm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem
import ws.worldshine.tomorrowfilm.model.FavoriteMovies
import ws.worldshine.tomorrowfilm.model.RemoteKeys

@Database(
        entities = [DiscoverMovieResultsItem::class, RemoteKeys::class, FavoriteMovies::class],
        version = 21,
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
                        MovieDatabaseNew::class.java, "movies.db")
                        .fallbackToDestructiveMigration()
                        .build()
    }
}