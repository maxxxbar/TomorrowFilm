package com.example.mymovies.datasource.movie;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.mymovies.Extra;
import com.example.mymovies.entries.discover.movie.Movies;
import com.example.mymovies.entries.discover.movie.Result;
import com.example.mymovies.network.Rest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DataSourceMovie extends PageKeyedDataSource<Integer, Result> {
    private String TAG = getClass().getSimpleName();
    private Rest restAPI;
    private final String SORT_BY;
    private final int VOTE_COUNT;

    public DataSourceMovie(Rest restAPI, String SORT_BY, int VOTE_COUNT) {
        this.restAPI = restAPI;
        this.SORT_BY = SORT_BY;
        this.VOTE_COUNT = VOTE_COUNT;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Result> callback) {
        List<Result> resultList = new ArrayList<>();
        restAPI.getMovies(Extra.API_KEY, Extra.LANGUAGE, SORT_BY, VOTE_COUNT, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Movies>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Movies movies) {
                        resultList.addAll(movies.getResults());
                        callback.onResult(resultList, null, 2);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {
        List<Result> resultList = new ArrayList<>();
        restAPI.getMovies(Extra.API_KEY, Extra.LANGUAGE, SORT_BY, VOTE_COUNT, params.key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Movies>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Movies movies) {
                        resultList.addAll(movies.getResults());
                        callback.onResult(resultList, params.key + 1);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }
}
