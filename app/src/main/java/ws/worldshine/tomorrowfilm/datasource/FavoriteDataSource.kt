package ws.worldshine.tomorrowfilm.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import ws.worldshine.tomorrowfilm.db.MovieDatabaseNew
import ws.worldshine.tomorrowfilm.model.FavoriteMovies
import javax.inject.Inject

class FavoriteDataSource @Inject constructor(private val db: MovieDatabaseNew) : PagingSource<Int, FavoriteMovies>() {
    companion object {
        private const val START_LIMIT = 0
    }

    private val TAG = javaClass.simpleName
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteMovies> {
        // TODO()  Пофиксить лимит Должно быть LIMIT :limit OFFSET :offset
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

    override fun getRefreshKey(state: PagingState<Int, FavoriteMovies>): Int? {
        return null
    }
}