package com.example.mymovies.ui.mainactivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.BindingExtra;
import com.example.mymovies.BuildConfig;
import com.example.mymovies.Extra;
import com.example.mymovies.R;
import com.example.mymovies.ui.favoriteactivity.FavoriteActivity;
import com.example.mymovies.adapters.MovieAdapter;
import com.example.mymovies.database.MainViewModel;
import com.example.mymovies.database.MovieDB;
import com.example.mymovies.databinding.ActivityMainBinding;
import com.example.mymovies.entries.discover.movie.Movies;
import com.example.mymovies.entries.discover.movie.Result;
import com.example.mymovies.etc.ProductDiffUtilCallback;
import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainActivityView {
    public static final int ACTIVITY_ID = 0;

    private ActivityMainBinding binding;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerViewPosters;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private MainActivityPresenter presenter;
    private com.google.android.material.switchmaterial.SwitchMaterial switchSortBy;
    private com.google.android.material.textview.MaterialTextView textViewPopularity;
    private com.google.android.material.textview.MaterialTextView textViewVoteAverage;
    private SharedPreferences sharedPreferences;
    private int sortBy = 0;
    private MainActivityViewModel mainActivityViewModel;

    private static int page = 1;


    private int previousTotal = 0;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private static boolean isLoading = true;

    private PagedList<Result> resultPagedList;
    private MainViewModel viewModel1;

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void setPage(int page) {
        MainActivity.page = page;
    }

    @Override
    public boolean getIsLoading() {
        return isLoading;
    }

    @Override
    public void setIsLoading(boolean isLoading) {
        MainActivity.isLoading = isLoading;
    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController = Navigation.findNavController(this, R.id.nav_host);
        BindingExtra bindingExtra = new BindingExtra(navController);
        binding.setBindingExtra(bindingExtra);
        mainActivityViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
 /*       if (BuildConfig.DEBUG) {
            // Create an InitializerBuilder
            Stetho.InitializerBuilder initializerBuilder =
                    Stetho.newInitializerBuilder(this);

            // Enable Chrome DevTools
            initializerBuilder.enableWebKitInspector(
                    Stetho.defaultInspectorModulesProvider(this)
            );

            // Enable command line interface
            initializerBuilder.enableDumpapp(
                    Stetho.defaultDumperPluginsProvider(this)
            );

            // Use the InitializerBuilder to generate an Initializer
            Stetho.Initializer initializer = initializerBuilder.build();

            // Initialize Stetho with the Initializer
            Stetho.initialize(initializer);
        }*/
/*        progressBar = binding.progressBar;
        textViewPopularity = binding.textViewPopularity;
        textViewVoteAverage = binding.textViewVoteAverage;*/
        /*RecyclerView*/
/*

        recyclerViewPosters = binding.recyclerViewPosters;
*/
        movieAdapter = new MovieAdapter();
/*
        mainActivityViewModel.getPagedListLiveData().observe(this, results -> movieAdapter.submitList(results));
*/
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);

/*        recyclerViewPosters.setLayoutManager(gridLayoutManager);
        recyclerViewPosters.setAdapter(movieAdapter);
        recyclerViewPosters.setHasFixedSize(true);*/
        /*RecyclerView*/
        /*Presenter*/
        presenter = new MainActivityPresenter(this);
        /*Presenter*/
        /*Switch*/
        /*switchSortBy = binding.switchSortBy;*/
        sharedPreferences = getSharedPreferences(Extra.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean checkSwitchState = sharedPreferences.getBoolean("switchState", false);
        /*switchSortBy.setChecked(checkSwitchState);*/
/*        switchSortBy.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                page = 1;
                sortByChecked(isChecked);
            }
        });
        *//*Switch*//*

        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                MovieDB movie = movieAdapter.getMovieDBList().get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", movie.getId());
                intent.putExtra("ACTIVITY_ID", ACTIVITY_ID);
                startActivity(intent);
            }


        });*/


/*        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                if (!isLoading) {
                    Toast.makeText(MainActivity.this, "Конец списка", Toast.LENGTH_SHORT).show();
                    presenter.getMoviesList(sortBy, page);
                }
            }
        });*/

/*        recyclerViewPosters.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = gridLayoutManager.getChildCount(); //смотрим сколько элементов на экране
                totalItemCount = gridLayoutManager.getItemCount(); //сколько всего элементов
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition(); //какая позиция первого элемента

 *//*               if (getIsLoading()) {
                    if (totalItemCount > previousTotal) {
                        setIsLoading(false);
                        previousTotal = totalItemCount;
                    }
                }
                if (!getIsLoading() && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    Toast.makeText(MainActivity.this, "Конец списка", Toast.LENGTH_SHORT).show();
                    presenter.getMoviesList(sortBy, page);

                }*//*
                if (!getIsLoading()) {
                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                        presenter.getMoviesList(sortBy, page);
                    }
                }

            }
        });*/
        presenter.showPostersOnStartActivity();
        viewModel1 = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
    }


    @Override
    public void showPosters(LiveData<PagedList<Movies>> viewModel) {

    }

    @Override
    public Application getApp() {
        return getApplication();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.disposeDisposable();
    }


    public void setRecyclerViewPosters(List<MovieDB> movies) {
        ProductDiffUtilCallback diffUtilCallback = new ProductDiffUtilCallback(movieAdapter.getMovieDBList(), movies);
        DiffUtil.DiffResult productResult = DiffUtil.calculateDiff(diffUtilCallback);
        movieAdapter.addToMovieDBList(movies);
        productResult.dispatchUpdatesTo(movieAdapter);

    }

/*    @Override
    public void showPosters(LiveData<PagedList<Movies>> viewModel) {
        viewModel.observe(this, new Observer<PagedList<Movies>>() {
            @Override
            public void onChanged(PagedList<Movies> movies) {

                movieAdapter.submitList(movies);

            }
        });
    }*/

/*    @Override
    public void showPosters(LiveData<List<MovieDB>> viewModel) {

        viewModel.observe(this, new Observer<List<MovieDB>>() {
                    @Override
                    public void onChanged(List<MovieDB> movieDBS) {

                        ProductDiffUtilCallback diffUtilCallback = new ProductDiffUtilCallback(movieAdapter.getMovieDBList(), movieDBS);
                        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
                        movieAdapter.addToMovieDBList(movieDBS);
                        diffResult.dispatchUpdatesTo(movieAdapter);
                    }
                }
        );
    }*/


    @Override
    protected void onPause() {
        super.onPause();
        /*saved switch status in shared preferences*/
/*
        SharedPreferences.Editor editor = getSharedPreferences(Extra.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean("switchState", switchSortBy.isChecked()).apply();
*/
        /*saved switch status in shared preferences*/
    }

    private void sortByChecked(boolean isChecked) {
        presenter.deleteAllMovieForSwitch();
        movieAdapter.clear();
        if (isChecked) {
            textViewVoteAverage.setTextColor(getColor(R.color.pink));
            textViewPopularity.setTextColor(getColor(R.color.whiteColor));
            sortBy = MainActivityPresenter.VOTE_AVERAGE;
        } else {
            textViewPopularity.setTextColor(getColor(R.color.pink));
            textViewVoteAverage.setTextColor(getColor(R.color.whiteColor));
            sortBy = MainActivityPresenter.POPULARITY;
        }
        presenter.getMoviesList(sortBy, getPage());
        Log.d("TAG", "sortByChecked нажат ");
    }

    public void onClickTextViewVoteAverage(View view) {
        switchSortBy.setChecked(true);
    }

    public void onClickTextViewPopularity(View view) {
        switchSortBy.setChecked(false);
    }

    @Override
    public void setProgressBar(int visibility) {
        progressBar.setVisibility(visibility);
    }


}
