package com.example.popularmoviesapp.ui.movie_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.model.Trailer;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private List<Trailer> mTrailersList;
    final private TrailerClickListener mOnClickLister;

    public TrailersAdapter (List<Trailer> trailersList, MovieDetailActivity listener){
        mTrailersList = trailersList;
        mOnClickLister = listener;
    }

    public interface TrailerClickListener {
        void onTrailerClick(Trailer trailer);
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.trailer_list_item, viewGroup, false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.bind(mTrailersList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTrailersList.size();
    }

    public void setTrailersList(List<Trailer> trailersList) {
        if (trailersList != null){
            mTrailersList.clear();
            mTrailersList.addAll(trailersList);
            notifyDataSetChanged();
        }
    }

    /** ViewHolder*/
    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTv;
        TextView typeTv;
        TextView platformTv;

        TrailerViewHolder(View itemView){
            super(itemView);

            nameTv = itemView.findViewById(R.id.name_trailer_tv);
            typeTv = itemView.findViewById(R.id.type_trailer_tv);
            platformTv = itemView.findViewById(R.id.platform_trailer_tv);

            itemView.setOnClickListener(this);
        }

        void bind (Trailer trailer) {
            nameTv.setText(trailer.getName());
            typeTv.setText(trailer.getType());
            platformTv.setText(trailer.getSite());
        }

        @Override
        public void onClick(View v) {
            Trailer trailer = mTrailersList.get(getAdapterPosition());
            mOnClickLister.onTrailerClick(trailer);
        }
    }
}