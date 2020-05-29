package com.example.popularmoviesapp.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.popularmoviesapp.ui.movie_detail.MovieDetailActivity;
import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.data.database.MovieEntry;
import com.example.popularmoviesapp.data.network.NetworkUtils;
import com.example.popularmoviesapp.utilities.InjectorUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    MainViewModel viewModel;

    private RecyclerView mMoviesListRv;
    private MoviesAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewModel();

        mMoviesListRv = findViewById(R.id.movies_list_rv);
        setupRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /** Check if is something new in Favorites to update the list if needed*/
        if (viewModel.getSortedBy().equals(NetworkUtils.SORT_BY_FAVORITES) && viewModel.hasNewFavUpdate()){
            viewModel.sortMoviesListBy(NetworkUtils.SORT_BY_FAVORITES);
        }
    }

    private void setupViewModel() {
        MainViewModelFactory factory = InjectorUtils.provideMainViewModelFactory(this.getApplicationContext());
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        viewModel.getMoviesListLiveData().observe(this, movieList -> {
            if (movieList.size() == 0 && !viewModel.getSortedBy().equals(NetworkUtils.SORT_BY_FAVORITES)) {
                showErrorMessage();
            }
            mAdapter.setNewList(movieList);
        });
    }

    private void setupRecyclerView(){
        GridLayoutManager layoutManager;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(this,2);
        } else {
            layoutManager = new GridLayoutManager(this,4);
        }
        mMoviesListRv.setLayoutManager(layoutManager);
        mMoviesListRv.setHasFixedSize(true);

        mAdapter = new MoviesAdapter(new ArrayList<>(), this);
        mMoviesListRv.setAdapter(mAdapter);
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
                viewModel.sortMoviesListBy(NetworkUtils.SORT_BY_MOST_POPULAR);
                break;
            case R.id.action_highest_rated:
                viewModel.sortMoviesListBy(NetworkUtils.SORT_BY_TOP_RATED);
                break;
            case R.id.action_favorites:
                viewModel.sortMoviesListBy(NetworkUtils.SORT_BY_FAVORITES);
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMoviePosterClick(MovieEntry movie) {
        launchMovieDetailActivity(movie);
    }

    private void launchMovieDetailActivity(MovieEntry movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieEntry.KEY_MOVIE, movie);
        startActivity(intent);
    }

    public void showErrorMessage(){
        Toast.makeText(MainActivity.this, R.string.error_message, Toast.LENGTH_LONG).show();
    }
}
