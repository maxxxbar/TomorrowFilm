package ws.worldshine.tomorrowfilm.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ws.worldshine.tomorrowfilm.R

fun ImageView.loadImageWithGlide(posterPath: String) {
    val url = Extra.POSTER_BASE_URL + Extra.BIG_POSTER_SIZE + posterPath
    Glide
            .with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(this)
}
