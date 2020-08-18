package com.example.mymovies.network;


import com.example.mymovies.Extra;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIConnection {
    private Retrofit retrofit;

    private APIConnection() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(Extra.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

    }

    public static final class APIConnectionHolder {
        public static final APIConnection HOLDER_INSTANCE = new APIConnection();
    }

    public static APIConnection getInstance() {
        return APIConnectionHolder.HOLDER_INSTANCE;
    }

    public RestAPI createGet() {
        return retrofit.create(RestAPI.class);
    }
}
