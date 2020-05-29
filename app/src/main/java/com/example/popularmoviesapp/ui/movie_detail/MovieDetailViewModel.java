package com.example.popularmoviesapp.ui.movie_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmoviesapp.data.PopularMoviesRepository;
import com.example.popularmoviesapp.data.database.MovieEntry;
import com.example.popularmoviesapp.data.network.NetworkUtils;
import com.example.popularmoviesapp.model.Review;
import com.example.popularmoviesapp.model.Trailer;

import java.util.List;

public class MovieDetailViewModel extends ViewModel {

    private final PopularMoviesRepository mRepository;
    private MovieEntry mMovie;
    private boolean isMovieFavorite;
    private Trailer trailerForShare;

    private final LiveData<List<Trailer>> mTrailersListLiveData;
    private final LiveData<List<Review>> mReviewsListLiveData;

    public MovieDetailViewModel(PopularMoviesRepository repo){
        mRepository = repo;
        mTrailersListLiveData = mRepository.getmTrailerListLiveData();
        mReviewsListLiveData = mRepository.getmReviewsListLiveData();
        isMovieFavorite = false;
    }

    public String getMovieId(){
        return mMovie.getId();
    }

    public String getPosterUrl(){
        return NetworkUtils.buildPosterUrl(mMovie.getPosterPath()).toString();
    }

    public void setMovie(MovieEntry movie) {
        mMovie = movie;
    }

    public MovieEntry getMovie() {
        return mMovie;
    }

    public LiveData<List<MovieEntry>> getFavoritesLiveData(){
        return mRepository.getFavoritesLiveData();
    }

    public boolean isMovieFavorite() {
        return isMovieFavorite;
    }

    void setIsMovieFavorite(boolean movieFavorite) {
        isMovieFavorite = movieFavorite;
    }

    /**
     * onFavoriteButtonPressed is called when the "favorite" button is clicked.
     * Add or remove a movie from the underlying favorites database
     * */
    public void onFavoriteButtonPressed (){
        if (isMovieFavorite) mRepository.removeFavorite(mMovie);
        else mRepository.addFavorite(mMovie);

    }

    public LiveData<List<Trailer>> getTrailersListLiveData() {
        return mTrailersListLiveData;
    }

    public LiveData<List<Review>> getReviewsListLiveData() {
        return mReviewsListLiveData;
    }

    public void setTrailers(){
        mRepository.fetchTrailersFromTMDB(mMovie.getId());
    }

    public void setReviews(){
        mRepository.fetchReviewsFromTMDB(mMovie.getId());
    }

    public Trailer getTrailerForShare() {
        return trailerForShare;
    }

    public void setTrailerForShare(Trailer movieTrailerForShare) {
        this.trailerForShare = movieTrailerForShare;
    }

    public String getTrailerUrl() {
        return NetworkUtils.getTrailerUrl(trailerForShare.getKey());
    }
}
