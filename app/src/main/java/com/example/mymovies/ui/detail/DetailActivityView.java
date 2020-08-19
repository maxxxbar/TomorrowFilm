package com.example.mymovies.ui.detail;

import android.app.Application;

import com.example.mymovies.entries.discover.reviews.Result;

import java.util.List;

public interface DetailActivityView {
    Application getApp();
    void setImageViewAddToFavorite(boolean itsFavorite);
    void setReviewAdapter(List<Result> result);
    void setTrailerAdapter(List<com.example.mymovies.entries.discover.trailer.Result> result);

}
