package com.example.mymovies.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.model.RemoteKeys
import com.example.mymovies.network.Rest
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class MovieRemoteMediator(
        private val restAPI: Rest,
        private val database: MovieDatabaseNew,
        private val sortBy: String,
        private val voteCount: Int
) : RemoteMediator<Int, DiscoverMovieResultsItem>() {
    companion object {
        private const val START_PAGE = 1
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, DiscoverMovieResultsItem>): MediatorResult {
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
                } else if (remoteKeys.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                } else {
                    remoteKeys.nextKey
                }
            }
        }

        try {
            val response = restAPI.getMovies2(sortBy = sortBy, voteCount = voteCount, page = page)
            var movies = listOf<DiscoverMovieResultsItem>()
            response.body()?.results?.let { movies = it }
            val endOfPaginationReached = movies.isEmpty()
            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.movieDao().clearMovies()
                }
                val prevKey = if (page == START_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    RemoteKeys(it.id, prevKey = prevKey, nextKey = nextKey)
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

    private suspend fun getRemoteKeyClosestToCurrentPosition(
            state: PagingState<Int, DiscoverMovieResultsItem>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                database.remoteKeysDao().remoteKeysMovieId(movieId)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DiscoverMovieResultsItem>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.remoteKeysDao().remoteKeysMovieId(movie.id)
        }
    }

}