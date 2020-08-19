package com.example.mymovies.datasource.movie;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.mymovies.Extra;
import com.example.mymovies.entries.discover.movie.Movies;
import com.example.mymovies.entries.discover.movie.Result;
import com.example.mymovies.network.RestAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DataSourceMovie extends PageKeyedDataSource<Integer, Result> {
    private String TAG = getClass().getSimpleName();
    private RestAPI restAPI;

    public DataSourceMovie(RestAPI restAPI) {
        this.restAPI = restAPI;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Result> callback) {
        List<Result> resultList = new ArrayList<>();
        restAPI.getMovieNew(Extra.API_KEY, Extra.LANGUAGE, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE,1)
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
        restAPI.getMovieNew(Extra.API_KEY, Extra.LANGUAGE, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE,params.key)
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
