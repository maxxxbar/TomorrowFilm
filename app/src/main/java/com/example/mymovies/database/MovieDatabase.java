package com.example.mymovies.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {MovieDB.class, FavoriteMovie.class}, version = 4, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DB_NAME = "movies.db";

    private static volatile MovieDatabase INSTANCE;
    public abstract MovieDao movieDao();

    public static MovieDatabase getInstance(Context context){
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

/*    public static MovieDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }

        return database;
    }

    public abstract MovieDao movieDao();*/
}