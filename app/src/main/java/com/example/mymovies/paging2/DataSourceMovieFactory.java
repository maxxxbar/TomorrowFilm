package com.example.mymovies.paging2;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.mymovies.entries.discover.movie.Result;
import com.example.mymovies.network.Rest;
import com.example.mymovies.paging2.DataSourceMovie;

import org.jetbrains.annotations.NotNull;

public class DataSourceMovieFactory extends DataSource.Factory<Integer, Result> {
    private DataSourceMovie dataSourceMovie;
    private MutableLiveData<DataSourceMovie> movieMutableLiveData;
    private Rest restAPI;
    private final String SORT_BY;
    private final int VOTE_COUNT;

    public DataSourceMovieFactory(Rest restAPI, String SORT_BY, int VOTE_COUNT) {
        movieMutableLiveData = new MutableLiveData<>();
        this.restAPI = restAPI;
        this.SORT_BY = SORT_BY;
        this.VOTE_COUNT = VOTE_COUNT;
    }


    @NotNull
    @Override
    public DataSource<Integer, Result> create() {
        dataSourceMovie = new DataSourceMovie(restAPI, SORT_BY, VOTE_COUNT);
        movieMutableLiveData.postValue(dataSourceMovie);
        return dataSourceMovie;
    }

    public MutableLiveData<DataSourceMovie> getMovieMutableLiveData() {
        return movieMutableLiveData;
    }
}
