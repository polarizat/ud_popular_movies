package com.example.popularmoviesapp.utilities;

import com.example.popularmoviesapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.LinkedList;

/**
 * Utility functions to handle TMDB JSON data.
 */
public final class TmdbJsonUtils {

    /* TMDB Model - Main fields */
    private static final String TMDBM_PAGE = "page";
    private static final String TMDBM_RESULTS = "results";
    private static final String TMDBM_TOTAL_RESULTS = "total_results";
    private static final String TMDBM_TOTAL_PAGES = "total_pages";
    private static final String TMDBM_MESSAGE_CODE = "cod";

    /* TMDB Model - Results fields */
    private static final String TMDBM_MOVIE_ID = "id";
    private static final String TMDBM_ORIGINAL_TITLE = "original_title";
    private static final String TMDBM_POSTER_PATH = "poster_path";
    private static final String TMDBM_RELEASE_DATE = "release_date";
    private static final String TMDBM_OVERVIEW = "overview";
    private static final String TMDBM_LANGUAGE = "original_language";
    private static final String TMDBM_VOTE_AVERAGE = "vote_average";


    public static LinkedList<Movie> getMoviesListFromJson(String moviesJsonStr)
            throws JSONException {

        LinkedList<Movie> moviesList = new LinkedList<>();

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        /* Is there an error? */
        if (moviesJson.has(TMDBM_MESSAGE_CODE)) {
            int errorCode = moviesJson.getInt(TMDBM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray moviesArray = moviesJson.getJSONArray(TMDBM_RESULTS);
        for (int i = 0; i < moviesArray.length(); i++) {

            /* Get the JSON object representing a movie */
            JSONObject movieObj = moviesArray.getJSONObject(i);

            String id = movieObj.getString(TMDBM_MOVIE_ID);
            String title = movieObj.getString(TMDBM_ORIGINAL_TITLE);
            String posterPath = movieObj.getString(TMDBM_POSTER_PATH);
            String releaseDate = movieObj.getString(TMDBM_RELEASE_DATE);
            String overview = movieObj.getString(TMDBM_OVERVIEW);
            String language = movieObj.getString(TMDBM_LANGUAGE);
            String voteAverage = movieObj.getString(TMDBM_VOTE_AVERAGE);

            Movie movie = new Movie(
                    id,
                    title,
                    posterPath,
                    releaseDate,
                    overview,
                    language,
                    voteAverage);

            moviesList.add(movie);
        }

        return moviesList;
    }
}