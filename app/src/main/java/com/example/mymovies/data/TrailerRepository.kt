package com.example.mymovies.data

import com.example.mymovies.entries.discover.trailer.Result
import com.example.mymovies.network.Rest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class TrailerRepository(private val rest: Rest) {
    fun getTrailers(movieId: Int): Single<List<Result>> {
        return rest.getTrailer(movieId = movieId)
                .map {
                    it.results
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}