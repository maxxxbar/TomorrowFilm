package com.example.mymovies.datasource

import androidx.paging.PagingSource
import com.bumptech.glide.load.HttpException
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.network.Rest
import java.io.IOException

class MoviePagingSource(
        private val restAPI: Rest,
        private val sortBy: String,
        private val voteCount: Int
) : PagingSource<Int, DiscoverMovieResultsItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMovieResultsItem> {
        val page = params.key ?: 1
        return try {
            val response = restAPI
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


