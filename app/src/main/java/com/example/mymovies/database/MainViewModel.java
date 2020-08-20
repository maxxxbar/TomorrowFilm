package com.example.mymovies.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.mymovies.entries.discover.movie.Movies;
import com.example.mymovies.entries.discover.movie.Result;
import com.example.mymovies.etc.DataSource;
import com.example.mymovies.etc.MovieDataSourceFactory;
import com.example.mymovies.network.APIConnection;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    private static MovieDatabase database;
    private LiveData<List<MovieDB>> movies;
    private LiveData<List<FavoriteMovie>> favoriteMovies;

    private LiveData<DataSource> dataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<Movies>> pagedListLiveData;

    public LiveData<List<MovieDB>> getMovies() {
        return movies;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(application);
        movies = database.movieDao().getAllMovies();
        favoriteMovies = database.movieDao().getAllFavoriteMovie();

        APIConnection connection = APIConnection.getInstance();
        MovieDataSourceFactory dataSourceFactory = new MovieDataSourceFactory(application, connection);
        dataSourceLiveData = dataSourceFactory.getMutableLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(40)
                .setPageSize(20)
                .setPrefetchDistance(2)
                .build();
        executor = Executors.newCachedThreadPool();
        pagedListLiveData = new LivePagedListBuilder<>(dataSourceFactory,
                config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Movies>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    public MovieDB getMovieById(int id) {
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FavoriteMovie getFavoriteMovieById(int id) {

        try {
            return new GetFavoriteTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIdFromFavoriteMovie(int id) {
        try {
            return new GetIdFromFavoriteMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void deleteAllMovies() {
        new DeleteAllMoviesTask().execute();
    }

    public void insertMovie(MovieDB movie) {
        new InsertTask().execute(movie);
    }

    public void deleteMovie(MovieDB movie) {
        new DeleteMovieTask().execute(movie);
    }

    public void insertFavoriteMovie(FavoriteMovie favoriteMovie) {
        new InsertFavoriteTask().execute(favoriteMovie);
    }

    public void deleteFavoriteMovie(FavoriteMovie favoriteMovie) {
        new DeleteFavoriteTask().execute(favoriteMovie);
    }

    public static class DeleteFavoriteTask extends AsyncTask<FavoriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            if (favoriteMovies != null && favoriteMovies.length > 0) {
                database.movieDao().deleteFavoriteMovie(favoriteMovies[0]);
            }
            return null;
        }
    }

    public static class InsertFavoriteTask extends AsyncTask<FavoriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            if (favoriteMovies != null && favoriteMovies.length > 0) {
                database.movieDao().insertFavoriteMovie(favoriteMovies[0]);
            }
            return null;
        }
    }

    public static class DeleteMovieTask extends AsyncTask<MovieDB, Void, Void> {
        @Override
        protected Void doInBackground(MovieDB... movieDBS) {
            if (movieDBS != null && movieDBS.length > 0) {
                database.movieDao().deleteMovie(movieDBS[0]);
            }
            return null;
        }
    }

    public static class InsertTask extends AsyncTask<MovieDB, Void, Void> {
        @Override
        protected Void doInBackground(MovieDB... movieDBS) {
            if (movieDBS != null && movieDBS.length > 0) {
                database.movieDao().insertMovie(movieDBS[0]);
            }
            return null;
        }
    }

    public static class DeleteAllMoviesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }

    public static class GetFavoriteTask extends AsyncTask<Integer, Void, FavoriteMovie> {
        @Override
        protected FavoriteMovie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getFavoriteMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class GetMovieTask extends AsyncTask<Integer, Void, MovieDB> {
        @Override
        protected MovieDB doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class GetIdFromFavoriteMovieTask extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getIdFromFavoriteMovie(integers[0]);
            }
            return null;
        }
    }
}