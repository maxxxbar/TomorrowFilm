package com.example.mymovies.datasource.movie

import androidx.paging.PagingSource
import com.example.mymovies.entries.discover.moviesnew.DiscoverMovieResultsItem
import com.example.mymovies.network.Rest
import com.example.mymovies.utils.Extra
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
        private val restAPI: Rest,
        private val sortBy: String,
        private val voteCount: Int
) : PagingSource<Int, DiscoverMovieResultsItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMovieResultsItem> {
/*        val result = mutableListOf<Result>()
        val page = params.key ?: 1
        restAPI.getMovies(Extra.API_KEY, Extra.LANGUAGE, sortBy, voteCount, page)
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    result.addAll(it.results)
                },
                        { Log.d("TAG", "load: ${it.localizedMessage}") })
        Log.d("TAG", "load: ${result.joinToString()}")
        return LoadResult.Page(
                data = result,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (result.isEmpty()) null else page + 1
        )*/

        val page = params.key ?: 1
        return try {
            val response = restAPI
                    .getMovies2(sortBy =  sortBy, voteCount =  voteCount,page =  page)
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


