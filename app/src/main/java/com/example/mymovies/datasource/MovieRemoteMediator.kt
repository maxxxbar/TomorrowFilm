package com.example.mymovies.datasource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mymovies.App
import com.example.mymovies.BuildConfig
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.di.component.DaggerMediatorComponent
import com.example.mymovies.di.module.NetworkModule
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.model.RemoteKeys
import com.example.mymovies.network.ConnectionAPI
import com.example.mymovies.network.Rest
import okhttp3.Interceptor
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRemoteMediator @Inject constructor(
        private val database: MovieDatabaseNew,
        private val sortBy: String,
        private val voteCount: Int
) : RemoteMediator<Int, DiscoverMovieResultsItem>() {
    private val TAG = javaClass.simpleName

    @Inject
    lateinit var rest: Rest

    init {

        val mediatorComponent = DaggerMediatorComponent.builder()
                .build()
                .networkBuilder()
                .networkModule(NetworkModule(interceptor = interceptor()))
                .build()
        mediatorComponent.inject(this)
        Log.d(TAG, "has code remoteMediator: ${hashCode()}")
    }


    companion object {
        private const val START_PAGE = 1
        const val API_KEY_QUERY = "api_key"
        const val APP_ID_VALUE = BuildConfig.API_KEY_TMDB
    }

/*    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }*/

    override suspend fun load(loadType: LoadType, state: PagingState<Int, DiscoverMovieResultsItem>): MediatorResult {
        Log.d(TAG, rest.toString())
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: START_PAGE
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null) {
                    START_PAGE
                } else remoteKeys.nextKey
                        ?: throw InvalidObjectException("Remote key should not be null for $loadType")
            }
        }

        try {
            val movies = rest.getMovies2(
                    sortBy = sortBy,
                    voteCount = voteCount,
                    page = page)
                    .body()?.results.orEmpty()
            val endOfPaginationReached = movies.isEmpty()
            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.movieDao().clearMovies()
                }
                val prevKey = if (page == START_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.d(TAG, "prevKey: $prevKey nextKey: $nextKey")
                Log.d(TAG, "endOfPaginationReached $endOfPaginationReached")
                val keys = movies.map {
                    RemoteKeys(movieId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.movieDao().insertAll(movies)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DiscoverMovieResultsItem>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item

        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { movie ->
                    // Get the remote keys of the last item retrieved
                    database.remoteKeysDao().remoteKeysMovieId(movie.id)
                }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
            state: PagingState<Int, DiscoverMovieResultsItem>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                database.remoteKeysDao().remoteKeysMovieId(movieId)
            }
        }
    }

    private fun interceptor(): Interceptor {
        return Interceptor.invoke { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(
                            API_KEY_QUERY,
                            APP_ID_VALUE
                    )
                    .build()
            val requestBuilder = original.newBuilder()
                    .url(url)
            chain.proceed(requestBuilder.build())
        }
    }

}