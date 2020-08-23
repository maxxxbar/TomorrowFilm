package com.example.mymovies.ui.mainactivity;


import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.ViewModelProvider;

import com.example.mymovies.BuildConfig;
import com.example.mymovies.database.MovieDataBaseViewModel;
import com.example.mymovies.database.MovieDB;
import com.example.mymovies.entries.discover.movie.Movies;
import com.example.mymovies.entries.discover.movie.Result;
import com.example.mymovies.network.APIConnection;
import com.example.mymovies.network.RestAPI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityPresenter {


    /*ViewModel*/
    private MovieDataBaseViewModel viewModel;
    /*ViewModel*/

    /*Переменные для запроса фильма*/
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public static final String API_KEY = "74f19a7f035e1b43395bec79650b05a8";
    public static final String LANGUAGE = "ru-RU";

    private static final boolean INCLUDE_ADULT = false;
    private static final boolean INCLUDE_VIDEO = false;
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_VOTE_AVERAGE = "vote_average.desc";
    private int VOTE_COUNT_GTE = 1000;


    public static final int POPULARITY = 0;
    public static final int VOTE_AVERAGE = 1;


    /*Конец Переменные для запроса фильмов*/

    /*Переменные запрос постера */
    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String SMALL_POSTER_SIZE = "w300";
    private static final String BIG_POSTER_SIZE = "w780";
    /*Конец Переменные запрос постера */
    private MainActivityView activityView;

    public MainActivityPresenter(MainActivityView activityView) {
        this.activityView = activityView;
    }

    public MainActivityPresenter() {
    }

    public void deleteAllMovieForSwitch() {
        /*ViewModel*/
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(activityView.getApp()).create(MovieDataBaseViewModel.class);
        /*ViewModel*/
        viewModel.deleteAllMovies();
    }

    /*Запрос фильмов*/
    public void getMoviesList(int sortBy, int page) {
        /*ViewModel*/
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(activityView.getApp()).create(MovieDataBaseViewModel.class);
        /*ViewModel*/

        String SORT_BY;
        if (sortBy == POPULARITY) {
            SORT_BY = SORT_BY_POPULARITY;
            VOTE_COUNT_GTE = 0;
        } else {
            SORT_BY = SORT_BY_VOTE_AVERAGE;
            VOTE_COUNT_GTE = 1000;
        }

        APIConnection connection = APIConnection.getInstance();
        RestAPI restAPI = connection.createGet();
        Observable<Movies> getMovieList = restAPI.getMovie(API_KEY, LANGUAGE, SORT_BY, page, VOTE_COUNT_GTE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Log.d("TAG", "VOTE_COUNT_GTE: " + VOTE_COUNT_GTE);
        getMovieList.subscribe(new Observer<Movies>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                activityView.setProgressBar(ProgressBar.VISIBLE);
                compositeDisposable.add(d);
                int i = activityView.getPage();
                i++;
                activityView.setPage(i);
                Log.d("TAG", "onSubscribe: activityView.setPage(i)" + i);
            }

            @Override
            public void onNext(@NonNull Movies movies) {
                for (int i = 0; i < movies.getResults().size(); i++) {
                    Result result = movies.getResults().get(i);
                    int id = result.getId();
                    int voteCount = result.getVoteCount();
                    String title = result.getTitle();
                    String originalTitle = result.getOriginalTitle();
                    String overview = result.getOverview();
                    String posterPath = POSTER_BASE_URL + SMALL_POSTER_SIZE + result.getPosterPath();
                    String bigPosterPath = POSTER_BASE_URL + BIG_POSTER_SIZE + result.getPosterPath();
                    String backdropPath = result.getBackdropPath();
                    double voteAverage = result.getVoteAverage();
                    String releaseDate = result.getReleaseDate();
                    MovieDB movie = new MovieDB(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);
                    viewModel.insertMovie(movie);

                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (BuildConfig.DEBUG) {
                    Log.d("TAG", "onError: " + e.getMessage());
                }
            }

            @Override
            public void onComplete() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                activityView.setProgressBar(ProgressBar.GONE);
                activityView.setIsLoading(false);


            }
        });
    }

    /*Конец Запрос фильмов*/

    public void showPostersOnStartActivity() {
        /*ViewModel*/
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(activityView.getApp()).create(MovieDataBaseViewModel.class);
        /*ViewModel*/
    }

    /*закрываем соединение*/
    void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
    /*закрываем соединение*/
}
