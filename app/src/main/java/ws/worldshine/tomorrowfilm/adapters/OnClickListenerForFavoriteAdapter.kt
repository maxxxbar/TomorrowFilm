package ws.worldshine.tomorrowfilm.adapters

import ws.worldshine.tomorrowfilm.model.FavoriteMovies

fun interface OnClickListenerForFavoriteAdapter {
    fun onClickListener(movie: FavoriteMovies)
}