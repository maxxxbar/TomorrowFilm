package ws.worldshine.tomorrowfilm.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import ws.worldshine.tomorrowfilm.R

fun createSnackBar(view: View, title: String, duration: Int): Snackbar = Snackbar.make(view, title, duration)

fun createSnackBar(view: View, title: String, duration: Int, titleAction: String, action: (View) -> Unit): Snackbar =
        Snackbar.make(view, title, Snackbar.LENGTH_LONG)
                .setAction(titleAction, action)

fun ImageView.loadImageWithGlide(posterPath: String) {
    val url = Extra.POSTER_BASE_URL + Extra.SMALL_POSTER_SIZE + posterPath
    Glide
            .with(this)
            .setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
            .load(url)
            .thumbnail(0.5f)
            .override(300, 300)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(this)
            .clearOnDetach()
}
