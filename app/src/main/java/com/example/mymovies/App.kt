package com.example.mymovies

import android.app.Application
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.datasource.MovieRemoteMediator
import com.example.mymovies.di.component.AppComponent
import com.example.mymovies.di.component.DaggerAppComponent
import com.example.mymovies.di.component.NetworkComponent
import com.example.mymovies.di.module.NetworkModule
import com.example.mymovies.network.Rest
import com.example.mymovies.utils.Extra
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import javax.inject.Inject

@ExperimentalPagingApi
class App() : Application() {
    private val TAG = javaClass.simpleName

    @Inject
    lateinit var rest: Rest

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }


    override fun onCreate() {
        super.onCreate()
      appComponent.networkBuilder().networkModule(NetworkModule(interceptor = interceptor())).build().inject(this)
      Log.d(TAG, rest.toString())
        /*   runBlocking(Dispatchers.IO) {
             launch {
                 rest.getMovies2(sortBy = Extra.SORT_BY_POPULARITY, voteCount = Extra.VOTE_COUNT_GTE, page = 1)
             }
         }*/
/*        val networkComponent: NetworkComponent = networkComponentBuilder
                .networkModule(NetworkModule())
                .build()
        networkComponent.inject(this)*/
    }

    private fun interceptor(): Interceptor {
        return Interceptor.invoke { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(
                            MovieRemoteMediator.API_KEY_QUERY,
                            MovieRemoteMediator.APP_ID_VALUE
                    )
                    .build()
            val requestBuilder = original.newBuilder()
                    .url(url)
            chain.proceed(requestBuilder.build())
        }
    }
}
