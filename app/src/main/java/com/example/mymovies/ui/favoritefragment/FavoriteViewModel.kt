package com.example.mymovies.ui.favoritefragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovies.data.FavoriteRepository
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.FavoriteMovies

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val db = MovieDatabaseNew.getInstance(application.applicationContext)
    private val repository = FavoriteRepository(db)
    fun getFavoriteMovies(): LiveData<PagingData<FavoriteMovies>> {
        return repository.getFavoriteMovies().cachedIn(viewModelScope)
    }
}