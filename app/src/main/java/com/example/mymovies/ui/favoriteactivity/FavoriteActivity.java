package com.example.mymovies.ui.favoriteactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mymovies.R;
import com.example.mymovies.ui.detail.DetailActivity;
import com.example.mymovies.adapters.MovieAdapter;
import com.example.mymovies.database.FavoriteMovie;
import com.example.mymovies.database.MovieDataBaseViewModel;
import com.example.mymovies.database.MovieDB;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    public static final int ACTIVITY_ID = 1;

    private RecyclerView recyclerViewFavoriteMovies;
    private MovieAdapter adapter;

    private MovieDataBaseViewModel viewModel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
/*            case R.id.itemMain:
                startActivity(new Intent(this, MainActivityJ.class));
                break;
            case R.id.itemFavorite:
                startActivity(new Intent(this, FavoriteActivity.class));
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
/*        recyclerViewFavoriteMovies = findViewById(R.id.recyclerViewFavoriteMovies);
        recyclerViewFavoriteMovies.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MovieAdapter();

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MovieDataBaseViewModel.class);
        LiveData<List<FavoriteMovie>> favoriteMovieLiveData = viewModel.getFavoriteMovies();
        favoriteMovieLiveData.observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                if (favoriteMovies != null) {
                    List<MovieDB> movies = new ArrayList<>(favoriteMovies);
                    adapter.setMovieDBList(movies);
                    recyclerViewFavoriteMovies.setAdapter(adapter);
                }

            }
        });

        adapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                MovieDB movieDB = adapter.getMovieDBList().get(position);

                Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
                intent.putExtra("id", movieDB.getId());
                intent.putExtra("ACTIVITY_ID", ACTIVITY_ID);
                startActivity(intent);
            }
        });*/
    }
}
