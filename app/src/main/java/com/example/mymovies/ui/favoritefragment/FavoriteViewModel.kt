package com.example.mymovies.ui.favoritefragment

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovies.data.FavoriteRepository
import com.example.mymovies.model.FavoriteMovies
import com.example.mymovies.ui.detailfragment.DetailFragment
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
        private val favoriteRepository: FavoriteRepository
) : ViewModel() {


    fun getFavoriteMovies(): LiveData<PagingData<FavoriteMovies>> {
        return favoriteRepository.getFavoriteMovies().cachedIn(viewModelScope)
    }

    fun setFavoriteMovieForDetailFragment(favoriteMovie: FavoriteMovies): Bundle =
            DetailFragment.setMovieBundle(favoriteMovie)
}