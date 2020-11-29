package com.example.mymovies.data

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
            val response = db.withTransaction {
                db.movieDao().getFavoriteMoviesByLimit(mStartLimit, mEndLimit)
            }
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