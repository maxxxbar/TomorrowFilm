package com.example.mymovies.activity.detail;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.BuildConfig;
import com.example.mymovies.R;
import com.example.mymovies.activity.favoriteactivity.FavoriteActivity;
import com.example.mymovies.activity.mainactivity.MainActivity;
import com.example.mymovies.adapters.ReviewAdapter;
import com.example.mymovies.adapters.TrailerAdapter;
import com.example.mymovies.database.FavoriteMovie;
import com.example.mymovies.database.MovieDB;
import com.example.mymovies.entries.discover.trailer.Result;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class DetailActivity extends AppCompatActivity implements DetailActivityView {
    public static final String API_KEY = "74f19a7f035e1b43395bec79650b05a8";
    public static final String LANGUAGE = "ru-RU";
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";


    private RecyclerView recyclerViewReviews;
    private RecyclerView recyclerViewTrailer;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewTitleRating;
    private TextView textViewReleaseDate;
    private TextView textViewDescription;

    private ImageView imageViewAddToFavorite;

    private DetailActivityPresenter presenter;

    private int id;
    private int ACTIVITY_ID;

    private List<String> trailerURL;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.itemFavorite:
                startActivity(new Intent(this, FavoriteActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Application getApp() {
        return getApplication();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewTitleRating = findViewById(R.id.textViewTitleRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageViewAddToFavorite = findViewById(R.id.imageViewAddToFavorite);
        /*FlexBox for RecyclerView*/
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);

        /*RecyclerViewVideo*/
        recyclerViewTrailer = findViewById(R.id.recyclerViewVideo);
        recyclerViewTrailer.setLayoutManager(new FlexboxLayoutManager(this, FlexDirection.COLUMN));
        recyclerViewTrailer.setHasFixedSize(true);
        trailerAdapter = new TrailerAdapter();
        trailerAdapter.setOnClickWatchTrailer(new TrailerAdapter.OnClickWatchTrailer() {
            @Override
            public void onTrailerClick(int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_URL + trailerURL.get(position)));
                startActivity(intent);
            }
        });
        /*RecyclerViewVideo*/

        /*RecyclerViewReviews*/
        reviewAdapter = new ReviewAdapter();
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        recyclerViewReviews.setLayoutManager(new FlexboxLayoutManager(this, FlexDirection.COLUMN));
        recyclerViewReviews.setHasFixedSize(true);
        /*RecyclerViewReviews*/

        presenter = new DetailActivityPresenter(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id") && intent.hasExtra("ACTIVITY_ID")) {
            id = intent.getIntExtra("id", -1);
            ACTIVITY_ID = intent.getIntExtra("ACTIVITY_ID", -1);
            if (BuildConfig.DEBUG) {
                Log.d("TAG", "id in getIntent: " + id);
                Log.d("TAG", "ACTIVITY_ID in getIntent: " + ACTIVITY_ID);
            }
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        if (ACTIVITY_ID == MainActivity.ACTIVITY_ID) {
            MovieDB movie = presenter.getMovieDetail(id);
            setMovieDBInActivity(movie);
        } else if (ACTIVITY_ID == FavoriteActivity.ACTIVITY_ID) {
            FavoriteMovie movies = presenter.getFavoriteMovie(id);
            setFavoriteMovieInActivity(movies);
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        presenter.checkFavorite();
        presenter.getReviewsList();
        presenter.getTrailerList();


    }

    public void setTrailerAdapter(List<Result> result) {
        trailerURL = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            trailerURL.add(result.get(i).getKey());
        }
        trailerAdapter.setTrailerList(result);
        recyclerViewTrailer.setAdapter(trailerAdapter);
    }

    @Override
    public void setReviewAdapter(List<com.example.mymovies.entries.discover.reviews.Result> result) {
        reviewAdapter.setReviews(result);
        recyclerViewReviews.setAdapter(reviewAdapter);
    }

    private void setMovieDBInActivity(MovieDB movie) {
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewTitleRating.setText(Double.toString(movie.getVoteAverage()));
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewDescription.setText(movie.getOverview());
    }

    private void setFavoriteMovieInActivity(FavoriteMovie movie) {
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewTitleRating.setText(Double.toString(movie.getVoteAverage()));
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewDescription.setText(movie.getOverview());
    }

    public void onCLickChangeFavorite(View view) {
        if (presenter.checkFavorite()) {
            if (BuildConfig.DEBUG) {
                Log.d("TAG", "onCLickChangeFavorite: Фильм в избранном, удаляем.");
            }
            presenter.addToFavorite();
            Toast.makeText(this, "Удалено из избранного", Toast.LENGTH_SHORT).show();
            setImageViewAddToFavorite(false);
        } else {
            presenter.addToFavorite();
            if (BuildConfig.DEBUG) {
                Log.d("TAG", "onCLickChangeFavorite: Фильм НЕ в избранном, добавляем.");
            }
            Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
            setImageViewAddToFavorite(true);
        }
    }

    public void setImageViewAddToFavorite(boolean itsFavorite) {
        if (itsFavorite) {
            imageViewAddToFavorite.setImageResource(R.drawable.favourite_remove);
        } else {
            imageViewAddToFavorite.setImageResource(R.drawable.favourite_add_to);
        }
    }
}
