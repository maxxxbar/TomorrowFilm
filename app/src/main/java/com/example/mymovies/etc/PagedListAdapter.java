package com.example.mymovies.etc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.database.MovieDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PagedListAdapter extends androidx.paging.PagedListAdapter<MovieDB, PagedListAdapter.PagedListAdapterViewHolder> {

    private List<MovieDB> movieDBList;

    public List<MovieDB> getMovieDBList() {
        return movieDBList;
    }

    public void setMovieDBList(List<MovieDB> movieDBList) {
        this.movieDBList = movieDBList;
    }

    public PagedListAdapter(@NonNull DiffUtil.ItemCallback<MovieDB> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PagedListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new PagedListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagedListAdapterViewHolder holder, int position) {
        Picasso picasso= Picasso.get();
        picasso.setIndicatorsEnabled(true);
        picasso.load(movieDBList.get(position).getPosterPath()).into(holder.imageViewSmallPoster);

    }

    class PagedListAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewSmallPoster;

        public PagedListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);

        }
    }
}
