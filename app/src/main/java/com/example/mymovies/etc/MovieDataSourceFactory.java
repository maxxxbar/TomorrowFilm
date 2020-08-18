package com.example.mymovies.etc;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.mymovies.entries.discover.movie.Movies;
import com.example.mymovies.network.APIConnection;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movies> {

    private com.example.mymovies.etc.DataSource dataSource;
    private Application application;
    private APIConnection connection;
    private MutableLiveData<com.example.mymovies.etc.DataSource> mutableLiveData;

    public MovieDataSourceFactory(Application application, APIConnection connection) {
        this.application = application;
        this.connection = connection;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Movies> create() {
        com.example.mymovies.etc.DataSource dataSource = new com.example.mymovies.etc.DataSource(connection,application);
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<com.example.mymovies.etc.DataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
