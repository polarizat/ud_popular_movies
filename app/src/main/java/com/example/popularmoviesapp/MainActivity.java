package com.example.popularmoviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.popularmoviesapp.model.Movie;
import com.example.popularmoviesapp.utilities.NetworkUtils;
import com.example.popularmoviesapp.utilities.TmdbJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener{

    private RecyclerView mMoviesListRv;
    private MoviesAdapter mAdapter;
    
    ProgressBar mProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesListRv = findViewById(R.id.movies_list_rv);
        mProgressIndicator = findViewById(R.id.progress_indicator_pb);

        makeTmdbQuery(NetworkUtils.QUERY_DEFAULT);
    }

    private void setupRecyclerView(LinkedList<Movie> movieList){
        GridLayoutManager layoutManager;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(this,2);
        } else {
            layoutManager = new GridLayoutManager(this,4);
        }
        mMoviesListRv.setLayoutManager(layoutManager);
        mMoviesListRv.setHasFixedSize(true);

        mAdapter = new MoviesAdapter(movieList, this);
        mMoviesListRv.setAdapter(mAdapter);
    }

    private void makeTmdbQuery(String sortByQuery) {

        URL tmdbUrl = NetworkUtils.buildMoviesQueryUrl(sortByQuery);
        new TmdbQueryTask().execute(tmdbUrl);

    }

    public class TmdbQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String tmdbSearchResults = null;
            try {
                tmdbSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tmdbSearchResults;
        }

        @Override
        protected void onPostExecute(String tmdbSearchResults) {
            mProgressIndicator.setVisibility(View.INVISIBLE); 
            if (tmdbSearchResults != null && !tmdbSearchResults.equals("")) {
                try {
                    LinkedList<Movie> movies = TmdbJsonUtils.getMoviesListFromJson(tmdbSearchResults);
                    setupRecyclerView(movies);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                showErrorMessage();
            }
        }
    }

    public void showErrorMessage(){
        Toast.makeText(MainActivity.this, R.string.erorr_message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_by_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int itemThatWasClicked = item.getItemId();

        switch (itemThatWasClicked) {
            case R.id.action_most_popular:
                makeTmdbQuery(NetworkUtils.QUERY_MOST_POPULAR);
                break;
            case R.id.action_highest_rated:
                makeTmdbQuery(NetworkUtils.QUERY_TOP_RATED);
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMoviePosterClick(Movie movie) {
        launchMovieDetailActivity(movie);
    }

    private void launchMovieDetailActivity(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Movie.KEY_MOVIE, movie);
        startActivity(intent);
    }
}
