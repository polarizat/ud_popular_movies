package com.example.popularmoviesapp.ui.main;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.popularmoviesapp.data.PopularMoviesRepository;

/**
 * Factory method that allows us to create a ViewModel with a constructor that takes a
 * {@link PopularMoviesRepository}
 */

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final PopularMoviesRepository mRepo;

    public MainViewModelFactory(PopularMoviesRepository repo) {
        this.mRepo = repo;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainViewModel(mRepo);
    }
}