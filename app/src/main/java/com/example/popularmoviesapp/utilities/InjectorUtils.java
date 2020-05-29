package com.example.popularmoviesapp.utilities;

import android.content.Context;

import com.example.popularmoviesapp.data.PopularMoviesRepository;
import com.example.popularmoviesapp.data.database.FavoritesMoviesDatabase;
import com.example.popularmoviesapp.ui.main.MainViewModelFactory;
import com.example.popularmoviesapp.ui.movie_detail.MovieDetailViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for Popular Movies App
 */
public class InjectorUtils {

    public static PopularMoviesRepository provideRepository(Context context) {
        FavoritesMoviesDatabase database = FavoritesMoviesDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return PopularMoviesRepository.getInstance(database.movieDao(), executors);
    }

    public static MovieDetailViewModelFactory provideMovieDetailViewModelFactory(Context context) {
        PopularMoviesRepository repository = provideRepository(context.getApplicationContext());
        return new MovieDetailViewModelFactory(repository);
    }

    public static MainViewModelFactory provideMainViewModelFactory(Context context) {
        PopularMoviesRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }
}