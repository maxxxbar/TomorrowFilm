package com.example.mymovies.etc;

import androidx.recyclerview.widget.DiffUtil;

import com.example.mymovies.database.MovieDB;

import java.util.List;

public class ProductDiffUtilCallback extends DiffUtil.Callback {

    private final List<MovieDB> oldList;
    private final List<MovieDB> newList;

    public ProductDiffUtilCallback(List<MovieDB> oldList, List<MovieDB> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }



    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        MovieDB oldMovie = oldList.get(oldItemPosition);
        MovieDB newMovie = newList.get(newItemPosition);

        return oldMovie.getId() == newMovie.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        MovieDB oldMovie = oldList.get(oldItemPosition);
        MovieDB newMovie = newList.get(newItemPosition);
        return oldMovie.getPosterPath().equals(newMovie.getPosterPath());
    }
}
