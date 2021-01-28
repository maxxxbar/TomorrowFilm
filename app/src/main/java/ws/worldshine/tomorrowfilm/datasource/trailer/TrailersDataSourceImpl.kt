package ws.worldshine.tomorrowfilm.datasource.trailer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ws.worldshine.tomorrowfilm.discover.trailer.Trailer
import ws.worldshine.tomorrowfilm.network.Rest

class TrailersDataSourceImpl(
        private val rest: Rest
) : TrailersDataSource {
    private val TAG = javaClass.simpleName
    override suspend fun getTrailers(movieId: Int): Trailer? {
        return try {
            withContext(Dispatchers.IO) {
                rest.getTrailers(movieId).body()
            }
        } catch (e: Exception) {
            null
        }
    }
}
