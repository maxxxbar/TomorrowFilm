package com.example.mymovies.ui.detailfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.withTransaction
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class DetailFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val databasse = MovieDatabaseNew.getInstance(application.applicationContext)
    private val TAG = javaClass.simpleName
    suspend fun getMovieFromDatabase(id: Int): Flow<DiscoverMovieResultsItem> {
        return databasse.withTransaction {
            Log.d(TAG, "getMovieFromDatabase DB: ${Thread.currentThread().name}")
            databasse.movieDao().getOneMovieAsLiveData(id)
        }
    }

}