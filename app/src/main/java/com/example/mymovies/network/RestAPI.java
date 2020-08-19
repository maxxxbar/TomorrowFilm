package com.example.mymovies.network;


import com.example.mymovies.entries.discover.movie.Movies;
import com.example.mymovies.entries.discover.reviews.Reviews;
import com.example.mymovies.entries.discover.trailer.Trailer;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestAPI {

    @GET("/3/discover/movie")
    public Observable<Movies> getMovie(@Query("api_key") String API_KEY,
                                       @Query("language") String LANGUAGE,
                                       @Query("sort_by") String SORT_BY,
                                       @Query("page") int PAGE,
                                       @Query("vote_count.gte") int VOTE_COUNT_GTE);

    @GET("/3/movie/{movie_id}/videos")
    public Observable<Trailer> getTrailer(@Path("movie_id") int MOVIE_ID,
                                          @Query("api_key") String API_KEY,
                                          @Query("language") String LANGUAGE);

    @GET("/3/movie/{movie_id}/reviews")
    public Observable<Reviews> getReviews(@Path("movie_id") int MOVIE_IE,
                                          @Query("api_key") String API_KEY,
                                          @Query("language") String LANGUAGE);


    @GET("/3/discover/movie")
    public Single<Movies> getMovieNew(@Query("api_key") String API_KEY,
                                      @Query("language") String LANGUAGE,
                                      @Query("sort_by") String SORT_BY,
                                      @Query("vote_count.gte") int VOTE_COUNT_GTE,
                                      @Query("page") int PAGE);


}

