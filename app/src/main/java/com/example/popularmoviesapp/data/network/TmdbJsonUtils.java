package com.example.popularmoviesapp.data.network;

import android.util.Log;

import com.example.popularmoviesapp.data.database.MovieEntry;
import com.example.popularmoviesapp.model.Review;
import com.example.popularmoviesapp.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

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

    public static List<MovieEntry> getMoviesListFromJson(String moviesJsonStr)
            throws JSONException {

        List<MovieEntry> moviesList = new LinkedList<>();
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

            String id = movieObj.getString(MovieEntry.TMDBM_MOVIE_ID);
            String title = movieObj.getString(MovieEntry.TMDBM_ORIGINAL_TITLE);
            String posterPath = movieObj.getString(MovieEntry.TMDBM_POSTER_PATH);
            String releaseDate = movieObj.getString(MovieEntry.TMDBM_RELEASE_DATE);
            String overview = movieObj.getString(MovieEntry.TMDBM_OVERVIEW);
            String language = movieObj.getString(MovieEntry.TMDBM_LANGUAGE);
            String voteAverage = movieObj.getString(MovieEntry.TMDBM_VOTE_AVERAGE);

            MovieEntry movie = new MovieEntry(id, title, posterPath, releaseDate, overview, voteAverage);
            Log.d(TAG, "getMoviesListFromJson: Movie: " + movie.getOriginalTitle());

            moviesList.add(movie);
        }
        return moviesList;
    }

    public static List<Review> getReviewsListFromJson(String reviewsJsonStr)
            throws JSONException {
        List<Review> reviewsList = new LinkedList<>();
        JSONObject reviewsJson = new JSONObject(reviewsJsonStr);

        /* Is there an error? */
        if (reviewsJson.has(TMDBM_MESSAGE_CODE)) {
            int errorCode = reviewsJson.getInt(TMDBM_MESSAGE_CODE);

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

        JSONArray reviewsArray = reviewsJson.getJSONArray(TMDBM_RESULTS);
        for (int i = 0; i < reviewsArray.length(); i++) {

            /* Get the JSON object representing a movie */
            JSONObject reviewObj = reviewsArray.getJSONObject(i);

            String id = reviewObj.getString(Review.TMDBM_KEY_ID);
            String author = reviewObj.getString(Review.TMDBM_KEY_AUTHOR);
            String content = reviewObj.getString(Review.TMDBM_KEY_CONTENT);
            String url = reviewObj.getString(Review.TMDBM_KEY_URL);

            Review review = new Review(id, author, content, url);

            reviewsList.add(review);
        }
        Log.d(TAG, "getReviewsListFromJson: Reviews nb: " + reviewsList.size());
        return reviewsList;
    }

    public static List<Trailer> getTrailersListFromJson(String trailersJsonStr)
            throws JSONException {
        List<Trailer> trailersList = new LinkedList<>();
        JSONObject trailersJson = new JSONObject(trailersJsonStr);

        /* Is there an error? */
        if (trailersJson.has(TMDBM_MESSAGE_CODE)) {
            int errorCode = trailersJson.getInt(TMDBM_MESSAGE_CODE);

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

        JSONArray trailersArray = trailersJson.getJSONArray(TMDBM_RESULTS);
        for (int i = 0; i < trailersArray.length(); i++) {

            /* Get the JSON object representing a movie */
            JSONObject trailerObj = trailersArray.getJSONObject(i);

            String id = trailerObj.getString(Trailer.TMDBM_KEY_ID);
            String name = trailerObj.getString(Trailer.TMDBM_KEY_NAME);
            String key = trailerObj.getString(Trailer.TMDBM_KEY_KEY);
            String site = trailerObj.getString(Trailer.TMDBM_KEY_SITE);
            String type = trailerObj.getString(Trailer.TMDBM_KEY_TYPE);

            Trailer trailer = new Trailer(id, name, key, site, type);

            trailersList.add(trailer);
        }
        return trailersList;
    }
}