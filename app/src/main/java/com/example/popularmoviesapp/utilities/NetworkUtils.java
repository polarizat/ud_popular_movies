package com.example.popularmoviesapp.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Utility functions to handle network requests.
 */
public class NetworkUtils {

    /** API KEY --------------------------------------------v  */
    private final static String apiKey = "Please enter a valid TMDB API Key";

    private final static String PARAM_API_KEY = "api_key";
    private final static String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String TMDB_BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    private final static String TMDB_POSTER_SIZE = "w342";

    private final static String PARAM_LANGUAGE = "language";
    private final static String language = "en-US";

    public final static String QUERY_MOST_POPULAR = "popular";
    public final static String QUERY_TOP_RATED = "top_rated";
    public final static String QUERY_DEFAULT = QUERY_MOST_POPULAR;


    public static URL buildMoviesQueryUrl(String sortByQuery) {
        Uri builtUri = Uri.parse(TMDB_BASE_URL)
                .buildUpon()
                .appendPath(sortByQuery)
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildPosterUrl(String posterPath) {
        Uri builtUri = Uri.parse(TMDB_BASE_POSTER_URL)
                .buildUpon()
                .appendPath(TMDB_POSTER_SIZE)
                .appendEncodedPath(posterPath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
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

}
