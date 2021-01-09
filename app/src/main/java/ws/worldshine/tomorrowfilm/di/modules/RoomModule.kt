package ws.worldshine.tomorrowfilm.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ws.worldshine.tomorrowfilm.db.MovieDatabaseNew
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