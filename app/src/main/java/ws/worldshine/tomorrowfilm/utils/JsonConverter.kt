package ws.worldshine.tomorrowfilm.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ws.worldshine.tomorrowfilm.model.DiscoverMovieItem

object JsonConverter {

    inline fun <reified T : DiscoverMovieItem> converter(json: String): DiscoverMovieItem {
        return Gson().fromJson(json, object : TypeToken<T>() {}.type)
    }

    fun converter(discoverMovieItem: DiscoverMovieItem): String {
        return Gson().toJson(discoverMovieItem)
    }
}