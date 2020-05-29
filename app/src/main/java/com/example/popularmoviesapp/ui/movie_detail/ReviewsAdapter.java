package com.example.popularmoviesapp.ui.movie_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp.R;
import com.example.popularmoviesapp.model.Review;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private List<Review> mReviewsList;

    public ReviewsAdapter (List<Review> reviewsList){
        mReviewsList = reviewsList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.review_list_item, viewGroup, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(mReviewsList.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

    public void setReviewsList(List<Review> reviewsList) {
        if (mReviewsList != null){
            mReviewsList.clear();
            mReviewsList.addAll(reviewsList);
            notifyDataSetChanged();
        }
    }

    /** ViewHolder*/
    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView authorTv;
        TextView contentTv;

        ReviewViewHolder(View itemView){
            super(itemView);

            authorTv = itemView.findViewById(R.id.author_reviewLi_tv);
            contentTv = itemView.findViewById(R.id.content_reviewLi_tv);
        }

        void bind (Review review) {
            authorTv.setText(review.getAuthor());
            contentTv.setText(review.getContent());
        }
    }
}
