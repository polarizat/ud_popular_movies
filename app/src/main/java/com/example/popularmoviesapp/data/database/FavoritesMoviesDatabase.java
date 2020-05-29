package com.example.popularmoviesapp.data.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MovieEntry.class}, version = 1)
public abstract class FavoritesMoviesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "favoritesMovie";

    private static final String LOG_TAG = FavoritesMoviesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static FavoritesMoviesDatabase sInstance;

    public static FavoritesMoviesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavoritesMoviesDatabase.class, FavoritesMoviesDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract FavoriteMovieDao movieDao();
}
