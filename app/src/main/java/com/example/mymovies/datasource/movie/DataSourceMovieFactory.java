package com.example.mymovies.datasource.movie;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.mymovies.entries.discover.movie.Result;
import com.example.mymovies.network.RestAPI;

public class DataSourceMovieFactory extends DataSource.Factory<Integer, Result> {
    private DataSourceMovie dataSourceMovie;
    private MutableLiveData<DataSourceMovie> movieMutableLiveData;
    private RestAPI restAPI;

    public DataSourceMovieFactory(RestAPI restAPI) {
        movieMutableLiveData = new MutableLiveData<>();
        this.restAPI = restAPI;
    }

    @Override
    public DataSource<Integer, Result> create() {
        dataSourceMovie = new DataSourceMovie(restAPI);
        movieMutableLiveData.postValue(dataSourceMovie);
        return dataSourceMovie;
    }

    public MutableLiveData<DataSourceMovie> getMovieMutableLiveData() {
        return movieMutableLiveData;
    }
}
