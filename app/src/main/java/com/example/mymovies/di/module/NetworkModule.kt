package com.example.mymovies.di.module

import com.example.mymovies.BuildConfig
import com.example.mymovies.di.MyScope
import com.example.mymovies.di.NetworkScope
import com.example.mymovies.network.Rest
import com.example.mymovies.utils.Extra
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
class NetworkModule(
        private val baseUrl: String = Extra.BASE_URL,
        private val interceptor: Interceptor? = null
) {
    @NetworkScope
    @Provides
    fun provideRetrofit(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory,
            rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava3CallAdapterFactory)
                .build()
    }
    @NetworkScope
    @Provides
    fun provideApi(retrofit: Retrofit): Rest =
            retrofit.create(Rest::class.java)
    @NetworkScope
    @Provides
    fun provideRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory =
            RxJava3CallAdapterFactory.create()
    @NetworkScope
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
            GsonConverterFactory.create()
    @NetworkScope
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
    @NetworkScope
    @Provides
    fun provideInterceptors(): ArrayList<Interceptor> {
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