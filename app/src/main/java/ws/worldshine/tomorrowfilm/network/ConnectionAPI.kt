package ws.worldshine.tomorrowfilm.network

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ws.worldshine.tomorrowfilm.utils.Extra

object ConnectionAPI {
    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor).build()
    private fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(Extra.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    val create: Rest = retrofit().create(Rest::class.java)
}








