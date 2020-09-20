package com.example.mymovies.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.entries.discover.movie.Result

class MovieAdapterNew2 : PagingDataAdapter<Result, RecyclerView.ViewHolder>(Result.CALLBACK) {
    var onFilmClickListener: OnFilmClickListener? = null

    inline fun setOnFilmClickListener(crossinline l: (Result) -> Unit) {
        this.onFilmClickListener = OnFilmClickListener { l(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieAdapterViewHolderNew2.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val result = getItem(position)
        if (result != null) {
            (holder as MovieAdapterViewHolderNew2).bind(result)
            holder.itemView.setOnClickListener {
                onFilmClickListener?.onClickListener(result)

            }
        }
    }


}