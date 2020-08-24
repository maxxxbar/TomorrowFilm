package com.example.mymovies.datasource.movie;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.mymovies.database.MovieDB;
import com.example.mymovies.database.MovieDao;

public class DataSourceFromRoom extends PositionalDataSource<MovieDB> {
    private MovieDao movieDao;

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<MovieDB> callback) {

    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<MovieDB> callback) {

    }
}
