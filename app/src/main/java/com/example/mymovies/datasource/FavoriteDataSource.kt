package com.example.mymovies.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.FavoriteMovies

class FavoriteDataSource(private val db: MovieDatabaseNew) : PagingSource<Int, FavoriteMovies>() {
    companion object {
        private const val START_LIMIT = 0
    }

    private val TAG = javaClass.simpleName
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteMovies> {
        val mStartLimit = params.key ?: START_LIMIT
        val mEndLimit = mStartLimit + 10
        return try {
            var response = emptyList<FavoriteMovies>()
            db.withTransaction {
                response = db.movieDao().getFavoriteMoviesByLimit(mStartLimit, mEndLimit)
                Log.d(TAG, "load: ${response.joinToString()}")
            }
            if (response.isEmpty()) Log.d(TAG, "Response is empty") else Log.d(TAG, "Response not empty")
            Log.d(TAG, "mStartLimit: $mStartLimit")
            Log.d(TAG, "mEndLimit: $mEndLimit")

            LoadResult.Page(
                    data = response,
                    prevKey = if (mStartLimit == START_LIMIT) null else mStartLimit - 10,
                    nextKey = if (response.isEmpty()) null else mStartLimit + 10
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}