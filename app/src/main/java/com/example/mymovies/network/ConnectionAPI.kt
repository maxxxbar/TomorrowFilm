package com.example.mymovies.network

import com.example.mymovies.Extra
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConnectionAPI {
    private val httpLoggingInterceptor = HttpLoggingInterceptor()
    private val client = OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build()

    fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(Extra.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    val create: Rest = retrofit().create(Rest::class.java)
}








