package com.example.mymovies.activity.detail;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.example.mymovies.BuildConfig;
import com.example.mymovies.adapters.ReviewAdapter;
import com.example.mymovies.database.FavoriteMovie;
import com.example.mymovies.database.MainViewModel;
import com.example.mymovies.database.MovieDB;
import com.example.mymovies.entries.discover.reviews.Result;
import com.example.mymovies.entries.discover.reviews.Reviews;
import com.example.mymovies.entries.discover.trailer.Trailer;
import com.example.mymovies.network.APIConnection;
import com.example.mymovies.network.RestAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

class DetailActivityPresenter {

    private int id = 0;
    private boolean itsFavorite;

    private MainViewModel viewModel;

    private DetailActivityView detailActivityView;

    DetailActivityPresenter(DetailActivity detailActivity) {
        this.detailActivityView = detailActivity;
    }


    MovieDB getMovieDetail(int id) {
        if (id > -1) {
            this.id = id;
            viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(detailActivityView.getApp()).create(MainViewModel.class);
            return viewModel.getMovieById(id);
        } else {
            if (BuildConfig.DEBUG) {
                Log.d("TAG", "getMovieDetail: " + id);
            }

        }
        return null;
    }


    boolean checkFavorite() {

        int idFromFavoriteMovieTable = viewModel.getIdFromFavoriteMovie(id);
        if (idFromFavoriteMovieTable > 0) {
            if (BuildConfig.DEBUG) {
                Log.d("TAG", "checkFavorite: Фильм в избраном: " + idFromFavoriteMovieTable);
            }
            detailActivityView.setImageViewAddToFavorite(true);
            return itsFavorite = true;
        } else {
            if (BuildConfig.DEBUG) {
                Log.d("TAG", "checkFavorite: Фильм НЕ в избраном: " + idFromFavoriteMovieTable);
            }
            detailActivityView.setImageViewAddToFavorite(false);
            return itsFavorite = false;
        }
    }

    void addToFavorite() {
        if (itsFavorite) {
            viewModel.deleteFavoriteMovie(viewModel.getFavoriteMovieById(id));
        } else {
            viewModel.insertFavoriteMovie(new FavoriteMovie(viewModel.getMovieById(id)));
        }
    }

    FavoriteMovie getFavoriteMovie(int id) {
        if (id > -1) {
            this.id = id;
            viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(detailActivityView.getApp()).create(MainViewModel.class);
            return viewModel.getFavoriteMovieById(id);
        } else {
            if (BuildConfig.DEBUG) {
                Log.d("TAG", "getMovieDetail: " + id);
            }
        }
        return null;
    }

    private RestAPI getRestAPI() {
        APIConnection connection = APIConnection.getInstance();
        return connection.createGet();
    }

    void getTrailerList() {
        Observable<Trailer> trailers = getRestAPI().getTrailer(id, DetailActivity.API_KEY, DetailActivity.LANGUAGE).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        trailers.subscribe(new Observer<Trailer>() {
            private List<com.example.mymovies.entries.discover.trailer.Result> trailerResult = new ArrayList<>();

            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Trailer trailer) {
                trailerResult.addAll(trailer.getResults());
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                if (BuildConfig.DEBUG) {
                    Log.d("TAG", "onError: " + e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                detailActivityView.setTrailerAdapter(trailerResult);
            }
        });
    }

    void getReviewsList() {
        Observable<Reviews> reviews = getRestAPI().getReviews(id, DetailActivity.API_KEY, "en-RN").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        reviews.subscribe(new Observer<Reviews>() {
            List<Result> reviewArrayList = new ArrayList<>();

            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Reviews reviews) {
                reviewArrayList.addAll(reviews.getResults());
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                if (BuildConfig.DEBUG) {
                    Log.d("TAG", "onError: " + e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                detailActivityView.setReviewAdapter(reviewArrayList);
            }
        });
    }


}
