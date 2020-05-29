package com.example.popularmoviesapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmoviesapp.data.database.FavoriteMovieDao;
import com.example.popularmoviesapp.data.database.MovieEntry;
import com.example.popularmoviesapp.data.network.NetworkUtils;
import com.example.popularmoviesapp.data.network.TmdbJsonUtils;
import com.example.popularmoviesapp.model.Review;
import com.example.popularmoviesapp.model.Trailer;
import com.example.popularmoviesapp.utilities.AppExecutors;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class PopularMoviesRepository {
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static PopularMoviesRepository sInstance;
    private final FavoriteMovieDao mFavoriteMovieDao;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private final MutableLiveData<List<MovieEntry>> mMoviesListLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Trailer>> mTrailerListLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> mReviewsListLiveData = new MutableLiveData<>();
    private LiveData<List<MovieEntry>> mFavoritesLiveData;
    private boolean hasNewFavUpdate = false;

    public synchronized static PopularMoviesRepository getInstance(FavoriteMovieDao favoriteMovieDao, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new PopularMoviesRepository(favoriteMovieDao, executors);
            }
        }
        return sInstance;
    }

    public PopularMoviesRepository(FavoriteMovieDao favoriteMovieDao, AppExecutors executors){
        mFavoriteMovieDao = favoriteMovieDao;
        mExecutors = executors;
        sortMoviesListBy(NetworkUtils.SORT_BY_DEFAULT);
        mFavoritesLiveData = favoriteMovieDao.getFavoriteMoviesLiveData();
    }

    public MutableLiveData<List<MovieEntry>> getMoviesListLiveData() {
        return mMoviesListLiveData;
    }

    public MutableLiveData<List<Trailer>> getmTrailerListLiveData() {
        return mTrailerListLiveData;
    }

    public MutableLiveData<List<Review>> getmReviewsListLiveData() {
        return mReviewsListLiveData;
    }

    public void sortMoviesListBy(String sortBy) {
        if (sortBy.equals(NetworkUtils.SORT_BY_FAVORITES)){
            fetchFavList();
        } else {
            fetchMoviesFromTMDB(sortBy);
        }
    }

    /*** Gets the movies*/
    public void fetchMoviesFromTMDB(String sortedBy) {
        mExecutors.networkIO().execute(() -> {
            try {
                URL tmdbRequestUrl = NetworkUtils.buildMoviesQueryUrl(sortedBy);
                String jsonTmdbResponse = NetworkUtils.getResponseFromHttpUrl(tmdbRequestUrl);
                if (jsonTmdbResponse != null && !jsonTmdbResponse.equals("")) {
                    List<MovieEntry> moviesList = TmdbJsonUtils.getMoviesListFromJson(jsonTmdbResponse);
                    mMoviesListLiveData.postValue(moviesList);
                } else {
                    mMoviesListLiveData.postValue(new LinkedList<>());
                }
            } catch (Exception e) {
                mMoviesListLiveData.postValue(new LinkedList<>());
                e.printStackTrace();
            }
        });
    }

    /*** Favorites */
    private void fetchFavList() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<MovieEntry> movieEntryList = mFavoriteMovieDao.getAllFavoriteMovies();
            mMoviesListLiveData.postValue(movieEntryList);
        });
        setHasNewFavUpdate(false);
    }
    public LiveData<List<MovieEntry>> getFavoritesLiveData() {
        return mFavoritesLiveData;
    }

    public void addFavorite(MovieEntry movie) {
        AppExecutors.getInstance().diskIO().execute(() ->
                mFavoriteMovieDao.insertFavoriteMovie(movie));
        setHasNewFavUpdate(true);
    }
    public void removeFavorite(MovieEntry movie) {
        AppExecutors.getInstance().diskIO().execute(() ->
                mFavoriteMovieDao.deleteFavoriteMovie(movie));
        setHasNewFavUpdate(true);
    }

    public boolean hasNewFavUpdate() {
        return hasNewFavUpdate;
    }
    public void setHasNewFavUpdate(boolean hasNewFavUpdate) {
        this.hasNewFavUpdate = hasNewFavUpdate;
    }

    /*** Gets the Trailers*/
    public void fetchTrailersFromTMDB(String movieId) {
        mExecutors.networkIO().execute(() -> {
            try {
                URL tmdbTrailersRequestUrl = NetworkUtils.buildTrailersUrl(movieId);
                String jsonTmdbResponse = NetworkUtils.getResponseFromHttpUrl(tmdbTrailersRequestUrl);

                if (jsonTmdbResponse != null && !jsonTmdbResponse.equals("")) {
                    List<Trailer> trailersList = TmdbJsonUtils.getTrailersListFromJson(jsonTmdbResponse);
                    mTrailerListLiveData.postValue(trailersList);
                } else {
                    mTrailerListLiveData.postValue(new LinkedList<>());
                }
            } catch (Exception e) {
                mTrailerListLiveData.postValue(new LinkedList<>());
                e.printStackTrace();
            }
        });
    }

    /*** Gets the Reviews*/
    public void fetchReviewsFromTMDB(String movieId) {
        mExecutors.networkIO().execute(() -> {
            try {
                URL tmdbReviewsRequestUrl = NetworkUtils.buildReviewsUrl(movieId);
                String jsonTmdbResponse = NetworkUtils.getResponseFromHttpUrl(tmdbReviewsRequestUrl);

                if (jsonTmdbResponse != null && !jsonTmdbResponse.equals("")) {
                    List<Review> reviewList = TmdbJsonUtils.getReviewsListFromJson(jsonTmdbResponse);
                    mReviewsListLiveData.postValue(reviewList);
                } else {
                    mReviewsListLiveData.postValue(new LinkedList<>());
                }
            } catch (Exception e) {
                mReviewsListLiveData.postValue(new LinkedList<>());
                e.printStackTrace();
            }
        });
    }

}
