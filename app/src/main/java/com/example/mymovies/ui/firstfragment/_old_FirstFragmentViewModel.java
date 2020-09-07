package com.example.mymovies.ui.firstfragment;

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
import com.example.mymovies.network.ConnectionAPI;
import com.example.mymovies.network.Rest;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class _old_FirstFragmentViewModel extends AndroidViewModel {
    private ConnectionAPI connection;
    private Rest rest;
    private Executor executor;
    private LiveData<DataSourceMovie> dataSourceMovieLiveData;
    private LiveData<PagedList<Result>> pagedListLiveData;
    private DataSourceMovieFactory dataSourceMovieFactory;

    public _old_FirstFragmentViewModel(@NonNull Application application) {
        super(application);
        connection = ConnectionAPI.INSTANCE;
        rest = connection.getCreate();
        dataSourceMovieFactory = new DataSourceMovieFactory(rest, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE);
        dataSourceMovieLiveData = dataSourceMovieFactory.getMovieMutableLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(2)
                .build();
        executor = Executors.newCachedThreadPool();

        pagedListLiveData = new LivePagedListBuilder<>(dataSourceMovieFactory, config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Result>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}
