package ws.worldshine.tomorrowfilm.datasource.trailer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ws.worldshine.tomorrowfilm.discover.trailer.Trailer
import ws.worldshine.tomorrowfilm.network.Rest

class TrailersDataSourceImpl(
        private val rest: Rest
) : TrailersDataSource {
    override suspend fun getTrailers(movieId: Int): Trailer {
        return withContext(Dispatchers.IO) {
            rest.getTrailers(movieId)
        }
    }
}