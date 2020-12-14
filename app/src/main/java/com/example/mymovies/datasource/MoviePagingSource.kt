package com.example.mymovies.datasource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.bumptech.glide.load.HttpException
import com.example.mymovies.di.component.DaggerMoviePagingSourceComponent
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.network.ConnectionAPI
import com.example.mymovies.network.Rest
import okhttp3.Interceptor
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class MoviePagingSource @Inject constructor(
        private val sortBy: String,
        private val voteCount: Int
) : PagingSource<Int, DiscoverMovieResultsItem>() {
    private val TAG = javaClass.simpleName

    @Inject lateinit var rest: Rest

    init {
        DaggerMoviePagingSourceComponent.builder()
                .build()
                .networkBuilder()
                .build()
                .inject(this)
        Log.d(TAG, "hash code moviePagingSource:${hashCode()} ")

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMovieResultsItem> {
        Log.d(TAG, rest.toString())
        val page = params.key ?: 1
        return try {
            val response = rest
                    .getMovies(sortBy = sortBy, voteCount = voteCount, page = page)
            val repos = response.body()
            var list = listOf<DiscoverMovieResultsItem>()
            repos?.let { discoverMovie ->
                discoverMovie.results?.let {
                    list = it
                }
            }
            LoadResult.Page(
                    data = list,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (list.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}


