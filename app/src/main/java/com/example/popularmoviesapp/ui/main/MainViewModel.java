package com.example.popularmoviesapp.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmoviesapp.data.PopularMoviesRepository;
import com.example.popularmoviesapp.data.database.MovieEntry;
import com.example.popularmoviesapp.data.network.NetworkUtils;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final PopularMoviesRepository mRepository;
    private String mSortedBy;

    public MainViewModel(PopularMoviesRepository repo){
        mRepository = repo;
        mSortedBy = NetworkUtils.SORT_BY_DEFAULT;
    }

    public LiveData<List<MovieEntry>> getMoviesListLiveData() {
        return mRepository.getMoviesListLiveData();
    }

    public void sortMoviesListBy(String sortedBy) {
        mSortedBy = sortedBy;
        mRepository.sortMoviesListBy(mSortedBy);
    }

    public String getSortedBy() {
        return mSortedBy;
    }

    public boolean hasNewFavUpdate() {
        return mRepository.hasNewFavUpdate();
    }
}
