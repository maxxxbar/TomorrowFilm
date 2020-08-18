package com.example.mymovies.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.mymovies.Extra;
import com.example.mymovies.R;
import com.squareup.picasso.Picasso;

public class BindingAdapters {
    private static String TAG = "BindingAdapters";

    @BindingAdapter({"app:url"})
    public static void loadImage(ImageView view, String url) {
        url = Extra.POSTER_BASE_URL + Extra.SMALL_POSTER_SIZE + url;
        Picasso picasso = Picasso.get();
        picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(view);
        picasso.setIndicatorsEnabled(true);
    }

}
