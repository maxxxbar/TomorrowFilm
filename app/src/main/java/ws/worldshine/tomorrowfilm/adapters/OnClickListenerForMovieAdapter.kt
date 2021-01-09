package ws.worldshine.tomorrowfilm.adapters

import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem

fun interface OnClickListenerForMovieAdapter {
    fun onClickListener(result: DiscoverMovieResultsItem)
}