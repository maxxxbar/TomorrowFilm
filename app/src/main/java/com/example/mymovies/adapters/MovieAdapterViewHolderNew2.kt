package com.example.mymovies.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovies.R
import com.example.mymovies.entries.discover.movie.Result
import com.example.mymovies.utils.Extra

class MovieAdapterViewHolderNew2(view: View) : RecyclerView.ViewHolder(view) {
    private val img = view.findViewById<ImageView>(R.id.imageViewSmallPoster)

    fun bind(result: Result?) {
        if (result !=null) {
            val url = Extra.POSTER_BASE_URL + Extra.SMALL_POSTER_SIZE + result.posterPath
            Glide
                    .with(img)
                    .load(url)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(img);
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieAdapterViewHolderNew2 {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movie_item, parent, false)
            return MovieAdapterViewHolderNew2(view)
        }
    }
}