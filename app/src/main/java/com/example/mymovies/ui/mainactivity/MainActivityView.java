package com.example.mymovies.ui.mainactivity;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.mymovies.database.MovieDB;
import com.example.mymovies.entries.discover.movie.Movies;

import java.util.List;

public interface MainActivityView {
    void showPosters(LiveData<PagedList<Movies>> viewModel);
    Application getApp();
    boolean getIsLoading();
    void setIsLoading(boolean isLoading);
    int getPage();
    void setPage(int page);
    void setRecyclerViewPosters(List<MovieDB> movies);
    void setProgressBar(int visibility);
}
