package com.example.mymovies.ui.mainactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.mymovies.Extra;
import com.example.mymovies.datasource.movie.DataSourceMovie;
import com.example.mymovies.datasource.movie.DataSourceMovieFactory;
import com.example.mymovies.entries.discover.movie.Result;
import com.example.mymovies.network.APIConnection;
import com.example.mymovies.network.RestAPI;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends AndroidViewModel {

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }
}
