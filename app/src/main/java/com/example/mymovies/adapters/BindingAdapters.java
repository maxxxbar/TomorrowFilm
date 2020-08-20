package com.example.mymovies.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.mymovies.Extra;
import com.example.mymovies.R;

public class BindingAdapters {
    @BindingAdapter({"app:url"})
    public static void loadImage(ImageView view, String url) {
        url = Extra.POSTER_BASE_URL + Extra.SMALL_POSTER_SIZE + url;
        Glide
                .with(view)
                .load(url)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(view);
    }
}
