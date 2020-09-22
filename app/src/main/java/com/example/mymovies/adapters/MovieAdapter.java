package com.example.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.utils.BindingExtra;
import com.example.mymovies.R;
import com.example.mymovies.database.MovieDB;
import com.example.mymovies.databinding.MovieItemBinding;
import com.example.mymovies.entries.discover.movie.Movies;
import com.example.mymovies.entries.discover.movie.Result;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends PagedListAdapter<Result, MovieAdapter.MovieAdapterViewHolder> {

    private List<MovieDB> movieDBList;
    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    public MovieAdapter() {
        super(Result.CALLBACK);
        this.movieDBList = new ArrayList<>();
    }

    public interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    public interface OnReachEndListener {
        void onReachEnd();
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_item, parent, false);
        return new MovieAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {

        Result movies = getItem(position);
/*        Picasso picasso = Picasso.get();
        picasso.load(movies.getPosterPath()).into(holder.imageViewSmallPoster);*/
        if (movies != null && movies.getPosterPath() != null && movies.getPosterPath().length() > 0) {
            BindingExtra.loadImage(holder.binding.imageViewSmallPoster, movies.getBackdropPath());
/*
            holder.binding.setMovieItem(movies);
*/
        }


    }


    class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        private MovieItemBinding binding;

        public MovieAdapterViewHolder(@NonNull MovieItemBinding movieItemBinding) {
            super(movieItemBinding.getRoot());
            this.binding = movieItemBinding;

/*            binding.getRoot().
                    setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onPosterClickListener != null) {
                                onPosterClickListener.onPosterClick(getAdapterPosition());
                            }
                        }
                    });*/
        }
    }

    public void addToMovieDBList(List<MovieDB> movieDBList) {
        this.movieDBList = movieDBList;

    }

    public void addToMovieDBList2(PagedList<Movies> movieDBList) {

    }


    public List<MovieDB> getMovieDBList() {
        return movieDBList;
    }

    public void setMovieDBList(List<MovieDB> movieDBList) {
        this.movieDBList.addAll(movieDBList);

    }

    public void clear() {
        this.movieDBList.clear();
        notifyDataSetChanged();
    }
}
