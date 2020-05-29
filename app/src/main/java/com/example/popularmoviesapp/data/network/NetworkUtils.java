package com.example.popularmoviesapp.data.network;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import  com.example.popularmoviesapp.BuildConfig;

/**
 * Utility functions to handle network requests.
 */
public class NetworkUtils {

    private final static String PARAM_API_KEY = "api_key";
    private final static String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    private final static String TMDB_BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private final static String TMDB_POSTER_SIZE = "w342";
    public final static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    private final static String PARAM_LANGUAGE = "language";
    private final static String language = "en-US";

    public final static String SORT_BY_MOST_POPULAR = "popular";
    public final static String SORT_BY_TOP_RATED = "top_rated";
    public final static String SORT_BY_FAVORITES = "favorites";
    public final static String SORT_BY_DEFAULT = SORT_BY_MOST_POPULAR;

    public final static String PARAM_REVIEWS = "reviews";
    public final static String PARAM_TRAILERS = "videos";


    public static URL buildMoviesQueryUrl(String sortByQuery) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL)
                .buildUpon()
                .appendPath(sortByQuery)
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.TMDB_API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .build();

        return buildUrl(builtUri);
    }

    public static URL buildPosterUrl(String posterPath) {
        Uri builtUri = Uri.parse(TMDB_BASE_POSTER_URL)
                .buildUpon()
                .appendPath(TMDB_POSTER_SIZE)
                .appendEncodedPath(posterPath)
                .build();

        return buildUrl(builtUri);
    }

    public static String getTrailerUrl(String trailerKey) {
        return String.format("%s%s", YOUTUBE_BASE_URL, trailerKey);
    }

    public static URL buildReviewsUrl(String movieId) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL)
                .buildUpon()
                .appendEncodedPath(movieId)
                .appendPath(PARAM_REVIEWS)
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.TMDB_API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .build();
        return buildUrl(builtUri);
    }

    public static URL buildTrailersUrl(String movieId) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL)
                .buildUpon()
                .appendEncodedPath(movieId)
                .appendPath(PARAM_TRAILERS)
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.TMDB_API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .build();

        return buildUrl(builtUri);
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static URL buildUrl(Uri uri){
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

}
