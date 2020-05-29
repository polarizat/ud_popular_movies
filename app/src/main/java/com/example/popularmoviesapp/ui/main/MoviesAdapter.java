package com.example.popularmoviesapp.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.data.database.MovieEntry;
import com.example.popularmoviesapp.data.network.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<MovieEntry> mMoviesList;
    final private ListItemClickListener mOnClickLister;

    public MoviesAdapter (List<MovieEntry> moviesList, ListItemClickListener listener){
        mMoviesList = moviesList;
        mOnClickLister = listener;
    }

    public interface ListItemClickListener{
        void onMoviePosterClick(MovieEntry movie);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
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

    public void setNewList(List<MovieEntry> list){
        if (list != null){
            mMoviesList.clear();
            mMoviesList.addAll(list);
            notifyDataSetChanged();
        }
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
            MovieEntry movie = mMoviesList.get(getAdapterPosition());
            mOnClickLister.onMoviePosterClick(movie);
        }
    }

}
