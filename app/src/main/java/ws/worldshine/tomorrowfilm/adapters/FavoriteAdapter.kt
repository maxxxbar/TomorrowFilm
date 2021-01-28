package ws.worldshine.tomorrowfilm.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ws.worldshine.tomorrowfilm.model.FavoriteMovies
import ws.worldshine.tomorrowfilm.viewholders.FavoriteAdapterViewHolder

class FavoriteAdapter : PagingDataAdapter<FavoriteMovies, FavoriteAdapterViewHolder>(COMPARATOR) {
    private var onClickListener: OnClickListenerForFavoriteAdapter? = null

    fun setOnFavoriteMovieClickListener(l: (FavoriteMovies) -> Unit) {
        this.onClickListener = OnClickListenerForFavoriteAdapter { l(it) }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<FavoriteMovies>() {
            override fun areItemsTheSame(oldItem: FavoriteMovies, newItem: FavoriteMovies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteMovies, newItem: FavoriteMovies): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: FavoriteAdapterViewHolder, position: Int) {
        val favoriteMovie = getItem(position)
        if (favoriteMovie != null) {
            favoriteMovie.posterPath?.let {
                holder.bind(it)
            }
            holder.itemView.setOnClickListener {
                onClickListener?.onClickListener(favoriteMovie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapterViewHolder {
        return FavoriteAdapterViewHolder.create(parent)
    }

}
