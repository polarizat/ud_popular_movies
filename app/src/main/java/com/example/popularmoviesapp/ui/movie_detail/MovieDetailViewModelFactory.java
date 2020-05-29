package com.example.popularmoviesapp.ui.movie_detail;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmoviesapp.data.PopularMoviesRepository;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link PopularMoviesRepository}}
 */

public class MovieDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final PopularMoviesRepository mRepo;

    public MovieDetailViewModelFactory(PopularMoviesRepository repo) {
        this.mRepo = repo;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MovieDetailViewModel(mRepo);
    }
}
