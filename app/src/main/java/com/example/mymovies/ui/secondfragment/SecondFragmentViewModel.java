package com.example.mymovies.ui.secondfragment;

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

public class SecondFragmentViewModel extends AndroidViewModel {
    private APIConnection connection;
    private RestAPI restAPI;
    private Executor executor;
    private LiveData<DataSourceMovie> dataSourceMovieLiveData;
    private LiveData<PagedList<Result>> pagedListLiveData;
    private DataSourceMovieFactory dataSourceMovieFactory;

    public SecondFragmentViewModel(@NonNull Application application) {
        super(application);
        connection = APIConnection.getInstance();
        restAPI = connection.createGet();
        dataSourceMovieFactory = new DataSourceMovieFactory(restAPI, Extra.SORT_BY_VOTE_AVERAGE, 0);
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