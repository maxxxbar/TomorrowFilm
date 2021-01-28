package ws.worldshine.tomorrowfilm.datasource.movie

import ws.worldshine.tomorrowfilm.model.DiscoverMovieItem

interface MovieDataSource {
    suspend fun getMovieById(id: Int): DiscoverMovieItem?
    suspend fun isFavoriteMovie(movieId: Int): Boolean
    suspend fun insertFavoriteMovie(discoverMovieItem: DiscoverMovieItem)
    suspend fun deleteFavoriteMovie(favoriteMovies: DiscoverMovieItem)
}