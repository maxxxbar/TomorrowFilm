package com.example.mymovies.ui.detailfragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.room.withTransaction
import com.example.mymovies.data.TrailerRepository
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.entries.discover.trailer.Result
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.network.ConnectionAPI
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

class DetailFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val databasse = MovieDatabaseNew.getInstance(application.applicationContext)
    private val rest = ConnectionAPI.create
    private val TAG = javaClass.simpleName
    suspend fun getMovieFromDatabase(id: Int): Flow<DiscoverMovieResultsItem> {
        return databasse.withTransaction {
            Log.d(TAG, "getMovieFromDatabase DB: ${Thread.currentThread().name}")
            databasse.movieDao().getOneMovieAsLiveData(id)
        }
    }

    fun getTrailers(movieId: Int): Single<List<Result>> {
        Log.d(TAG, "getTrailersVM: ")
        return TrailerRepository(rest).getTrailers(movieId)
    }

}