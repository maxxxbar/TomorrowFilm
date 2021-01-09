package ws.worldshine.tomorrowfilm.datasource.trailer

import ws.worldshine.tomorrowfilm.discover.trailer.Trailer

interface TrailersDataSource {
    suspend fun getTrailers(movieId: Int): Trailer
}