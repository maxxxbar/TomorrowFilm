package com.example.mymovies.etc;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.mymovies.ui.mainactivity.MainActivityPresenter;
import com.example.mymovies.entries.discover.movie.Movies;
import com.example.mymovies.network.APIConnection;
import com.example.mymovies.network.RestAPI;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DataSource extends PageKeyedDataSource<Integer, Movies> {

    private APIConnection connection;

    public DataSource(APIConnection connection) {
        this.connection = connection;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movies> callback) {

        RestAPI restAPI = connection.createGet();
        Observable<Movies> getMovieList = restAPI.getMovie(MainActivityPresenter.API_KEY,
                MainActivityPresenter.LANGUAGE,
                "popularity.desc",
                1,
                1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ArrayList<Movies> moviesArrayList = new ArrayList<>();

        getMovieList.subscribe(new Observer<Movies>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Movies movies) {
                moviesArrayList.add(movies);
            }


            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                callback.onResult(moviesArrayList, null, 2);

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movies> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movies> callback) {
        Log.d("TAG", "loadAfter: ");
        RestAPI restAPI = connection.createGet();
        Observable<Movies> getMovieList = restAPI.getMovie(MainActivityPresenter.API_KEY,
                MainActivityPresenter.LANGUAGE,
                "popularity.desc",
                1,
                1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        ArrayList<Movies> moviesArrayList = new ArrayList<>();

        getMovieList.subscribe(new Observer<Movies>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Movies movies) {
                moviesArrayList.add(movies);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                callback.onResult(moviesArrayList, params.key + 1);

            }
        });


    }
}
