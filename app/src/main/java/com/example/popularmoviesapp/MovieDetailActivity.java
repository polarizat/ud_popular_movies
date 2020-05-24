package com.example.popularmoviesapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmoviesapp.model.Movie;
import com.example.popularmoviesapp.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    Movie mMovie;

    ImageView mPosterIv;
    TextView mTitleTv;
    TextView mOverview;
    TextView mReleaseDate;
    TextView mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mPosterIv = findViewById(R.id.poster_iv);
        mTitleTv = findViewById(R.id.title_org_tv);
        mOverview = findViewById(R.id.overview_tv);
        mReleaseDate = findViewById(R.id.release_date_tv);
        mRating = findViewById(R.id.rating_tv);

        if(getIntent().getExtras() != null) {
            mMovie = getIntent().getParcelableExtra(Movie.KEY_MOVIE);

            mTitleTv.setText(mMovie.getOriginalTitle());
            mOverview.setText(mMovie.getOverview());
            mReleaseDate.setText(mMovie.getReleaseDate().substring(0,4));
            mRating.setText(String.format(mMovie.getVoteAverage(), getString(R.string.rating_format)));

            String posterUrl = NetworkUtils.buildPosterUrl(mMovie.getPosterPath()).toString();
            Picasso.get()
                    .load(posterUrl)
                    .fit()
                    .placeholder(R.color.colorPrimaryDark)
                    .error(R.color.colorAccent)
                    .into(mPosterIv);

        } else {
           showErrorMessage();
        }
    }

    public void showErrorMessage(){
        Toast.makeText(MovieDetailActivity.this, R.string.erorr_message, Toast.LENGTH_LONG).show();
    }
}
