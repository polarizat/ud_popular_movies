package com.example.popularmoviesapp.common;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

public class BindingAdapters{

    @BindingAdapter("imageUrl")
     public static void setPosterUrl(ImageView imageView, String posterUrl){
            Picasso.get()
            .load(posterUrl)
            .placeholder(R.color.colorPrimary)
            .error(R.color.colorAccent)
            .into(imageView);
    }
}