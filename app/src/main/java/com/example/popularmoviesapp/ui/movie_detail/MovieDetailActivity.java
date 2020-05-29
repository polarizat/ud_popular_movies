package com.example.popularmoviesapp.ui.movie_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.data.database.MovieEntry;
import com.example.popularmoviesapp.data.network.NetworkUtils;
import com.example.popularmoviesapp.databinding.ActivityMovieDetailsBinding;
import com.example.popularmoviesapp.model.Trailer;
import com.example.popularmoviesapp.utilities.InjectorUtils;

import java.util.LinkedList;

public class MovieDetailActivity extends AppCompatActivity implements
        TrailersAdapter.TrailerClickListener{

    MovieDetailViewModel viewModel;
    ActivityMovieDetailsBinding binding;

    RecyclerView mTrailersRv;
    RecyclerView mReviewsRv;
    TrailersAdapter mTrailersAdapter;
    ReviewsAdapter mReviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTrailersRv = findViewById(R.id.trailers_rv);
        mReviewsRv = findViewById(R.id.reviews_rv);

        if(getIntent().getExtras() != null) {
            setupViewModel(getIntent().getParcelableExtra(MovieEntry.KEY_MOVIE));
            binding.setViewModel(viewModel);
            binding.shareTrailerIb.setOnClickListener(view -> {
                shareTrailer();
            });
            setupTrailersRv();
            setupReviewsRv();
        } else {
            showErrorMessage();
        }
    }

    private void setupViewModel(MovieEntry movieEntry) {
        MovieDetailViewModelFactory factory = InjectorUtils.provideMovieDetailViewModelFactory(this.getApplicationContext());
        viewModel = new ViewModelProvider(this, factory).get(MovieDetailViewModel.class);
        viewModel.setMovie(movieEntry);

        viewModel.getFavoritesLiveData().observe(this, favoriteMovies -> {
            for (MovieEntry favoriteMovie : favoriteMovies){
                if (favoriteMovie.getId().equals(viewModel.getMovieId())){
                    viewModel.setIsMovieFavorite(true);
                    binding.addToFavIv.setActivated(true);
                    return;
                }
            }
            viewModel.setIsMovieFavorite(false);
        });
        viewModel.getTrailersListLiveData().observe(this, trailers ->{
                    if (trailers.size() == 0) {
                        mTrailersAdapter.setTrailersList(null);
                        showOffline();
                    } else {
                        hideOffline();
                        viewModel.setTrailerForShare(trailers.get(0));
                    }
                    mTrailersAdapter.setTrailersList(trailers);
                }
        );
        viewModel.getReviewsListLiveData().observe(this, reviews -> {
                    mReviewsAdapter.setReviewsList(reviews);
                    if (reviews.size() == 0) {
                        binding.reviewLayout.reviewsNoReviews.setVisibility(View.VISIBLE);
                    } else binding.reviewLayout.reviewsNoReviews.setVisibility(View.GONE);
                }
        );
    }

    public void setupTrailersRv(){
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        mTrailersRv.setLayoutManager(layoutManager);
        mTrailersRv.setHasFixedSize(true);
        mReviewsRv.setItemViewCacheSize(10);
        mTrailersAdapter = new TrailersAdapter(new LinkedList<>(), this);
        mTrailersRv.setAdapter(mTrailersAdapter);
        viewModel.setTrailers();
    }
    public void setupReviewsRv(){
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        mReviewsRv.setLayoutManager(layoutManager);
        mReviewsRv.setHasFixedSize(true);
        mReviewsRv.setItemViewCacheSize(10);
        mReviewsRv.setNestedScrollingEnabled(false);
        mReviewsAdapter = new ReviewsAdapter(new LinkedList<>());
        mReviewsRv.setAdapter(mReviewsAdapter);
        viewModel.setReviews();
    }

    @Override
    public void onTrailerClick(Trailer trailer) {
        openTrailerInYouTube(trailer.getKey());
    }
    private void openTrailerInYouTube(String key){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(NetworkUtils.YOUTUBE_BASE_URL + key));
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
    private void shareTrailer(){
        Trailer trailerToShare = viewModel.getTrailerForShare();
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_SUBJECT, trailerToShare.getName());
        intentShare.putExtra(Intent.EXTRA_TEXT, viewModel.getTrailerUrl());
        startActivity(Intent.createChooser(intentShare, "Share Trailer URL"));
    }

    public void showOffline(){
        binding.offlineMessageTv.setVisibility(View.VISIBLE);
        binding.reviewLayout.reviewsLl.setVisibility(View.GONE);
        binding.trailersLayout.trailersLl.setVisibility(View.GONE);
    }
    public void hideOffline(){
        binding.offlineMessageTv.setVisibility(View.GONE);
        binding.reviewLayout.reviewsLl.setVisibility(View.VISIBLE);
        binding.trailersLayout.trailersLl.setVisibility(View.VISIBLE);
    }
    public void showErrorMessage(){
        Toast.makeText(MovieDetailActivity.this, R.string.error_message, Toast.LENGTH_LONG).show();
    }
}
