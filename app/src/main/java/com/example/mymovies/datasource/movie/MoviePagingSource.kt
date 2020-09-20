package com.example.mymovies.datasource.movie

import android.util.Log
import androidx.paging.PagingSource
import com.example.mymovies.entries.discover.movie.Result
import com.example.mymovies.network.Rest
import com.example.mymovies.utils.Extra
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
        private val restAPI: Rest,
        private val sortBy: String,
        private val voteCount: Int
) : PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
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

        val position = params.key ?: 1
        return try {
            val response = restAPI.getMovies2(Extra.API_KEY, Extra.LANGUAGE, sortBy, voteCount, position)
            val repos = response.body()
            var list = mutableListOf<Result>()
            if (repos != null) {
                list = repos.results
            }
            LoadResult.Page(
                    data = list,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (list.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}


