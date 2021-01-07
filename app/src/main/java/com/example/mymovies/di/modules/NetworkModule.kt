package com.example.mymovies.di.modules

import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.BuildConfig
import com.example.mymovies.network.Rest
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    private companion object {
        private const val API_KEY_QUERY = "api_key"
        private const val APP_ID_VALUE = BuildConfig.API_KEY_TMDB
        private const val BASE_URL = "https://api.themoviedb.org"
    }

    @Singleton
    @Provides
    fun provideRetrofit(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory,
            rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava3CallAdapterFactory)
                .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Rest =
            retrofit.create(Rest::class.java)

    @Singleton
    @Provides
    fun provideRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory =
            RxJava3CallAdapterFactory.create()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
            GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(
            interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        return clientBuilder.build()
    }


    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun interceptor(): Interceptor {
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

    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun provideInterceptors(interceptor: Interceptor?): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        if (interceptor != null) {
            interceptors.add(interceptor)
        }
        interceptors.add(loggingInterceptor)
        return interceptors
    }
}