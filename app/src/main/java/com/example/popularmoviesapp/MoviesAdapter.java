package com.example.popularmoviesapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp.model.Movie;
import com.example.popularmoviesapp.utilities.NetworkUtils;
import com.example.popularmoviesapp.utilities.TmdbJsonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.LinkedList;

import static android.content.ContentValues.TAG;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private LinkedList<Movie> mMoviesList;
    final private ListItemClickListener mOnClickLister;

    public MoviesAdapter (LinkedList<Movie> moviesList, ListItemClickListener listener){
        mMoviesList = moviesList;
        mOnClickLister = listener;
    }

    public interface ListItemClickListener{
        void onMoviePosterClick(Movie movie);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        URL posterUrl = NetworkUtils.buildPosterUrl(mMoviesList.get(position).getPosterPath());
        holder.bind(posterUrl);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }


    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePosterIv;

        MovieViewHolder(View itemView){
            super(itemView);

            moviePosterIv = itemView.findViewById(R.id.movie_poster_iv);
            itemView.setOnClickListener(this);
        }

        void bind (URL posterUrl) {
            Picasso.get()
                    .load(posterUrl.toString())
                    .fit()
                    .placeholder(R.color.colorPrimary)
                    .error(R.color.colorAccent)
                    .into(moviePosterIv);
        }

        @Override
        public void onClick(View v) {
            Movie movie = mMoviesList.get(getAdapterPosition());
            mOnClickLister.onMoviePosterClick(movie);
        }
    }

}
