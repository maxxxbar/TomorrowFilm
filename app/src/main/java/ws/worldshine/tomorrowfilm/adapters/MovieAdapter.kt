package ws.worldshine.tomorrowfilm.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ws.worldshine.tomorrowfilm.databinding.MovieItemBinding
import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem
import ws.worldshine.tomorrowfilm.utils.loadImageWithGlide
import ws.worldshine.tomorrowfilm.viewholders.MovieAdapterViewHolderNew

class MovieAdapter : PagingDataAdapter<DiscoverMovieResultsItem, MovieAdapterViewHolderNew>(COMPARATOR) {

    private var onClickListener: OnClickListenerForMovieAdapter? = null

    fun setOnFilmClickListener(l: (DiscoverMovieResultsItem) -> Unit) {
        this.onClickListener = OnClickListenerForMovieAdapter { l(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolderNew {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieAdapterViewHolderNew(binding)
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: MovieAdapterViewHolderNew, position: Int) {
        val movies = getItem(position)
        if (movies?.posterPath != null && movies.posterPath.isNotEmpty()) {
            holder.apply {
                bind(movies.posterPath)
                binding.imageViewSmallPoster.loadImageWithGlide(movies.posterPath)
                itemView.setOnClickListener {
                    onClickListener?.onClickListener(movies)
                }
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<DiscoverMovieResultsItem>() {

            override fun areItemsTheSame(oldItem: DiscoverMovieResultsItem, newItem: DiscoverMovieResultsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DiscoverMovieResultsItem, newItem: DiscoverMovieResultsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}