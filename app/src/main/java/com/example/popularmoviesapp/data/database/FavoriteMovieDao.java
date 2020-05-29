package com.example.popularmoviesapp.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM movies ORDER BY vote_average DESC")
    LiveData<List<MovieEntry>> getFavoriteMoviesLiveData();

    @Query("SELECT * FROM movies ORDER BY vote_average DESC")
    List<MovieEntry> getAllFavoriteMovies();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(MovieEntry movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavoriteMovie(MovieEntry movie);

    @Delete
    void deleteFavoriteMovie(MovieEntry movie);

}
