package com.example.mymovies.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.navigation.NavController;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mymovies.R;

public class BindingExtra {
    private NavController navController;

    public BindingExtra(NavController navController) {
        this.navController = navController;
    }

    @BindingAdapter({"app:url"})
    public static void loadImage(ImageView view, String url) {
        url = Extra.POSTER_BASE_URL + Extra.SMALL_POSTER_SIZE + url;
        Glide
                .with(view)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(view);
    }

    public void onCLickFirst(View view) {
        navController.navigate(R.id.firstFragment);
    }

    public void onCLickSecond(View view) {
        navController.navigate(R.id.secondFragment);
    }
}
