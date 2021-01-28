package ws.worldshine.tomorrowfilm.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ws.worldshine.tomorrowfilm.databinding.MovieItemBinding
import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem
import ws.worldshine.tomorrowfilm.utils.loadImageWithGlide
import ws.worldshine.tomorrowfilm.viewholders.MovieAdapterViewHolder

class MovieAdapter : PagingDataAdapter<DiscoverMovieResultsItem, MovieAdapterViewHolder>(COMPARATOR) {
    private val TAG = javaClass.simpleName
    private var onClickListener: OnClickListenerForMovieAdapter? = null

    fun setOnFilmClickListener(l: (DiscoverMovieResultsItem) -> Unit) {
        this.onClickListener = OnClickListenerForMovieAdapter { l(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieAdapterViewHolder(binding)
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: MovieAdapterViewHolder, position: Int) {
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

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            1
        } else {
            0
        }


    }

    private companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<DiscoverMovieResultsItem>() {

            override fun areItemsTheSame(oldItem: DiscoverMovieResultsItem, newItem: DiscoverMovieResultsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DiscoverMovieResultsItem, newItem: DiscoverMovieResultsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}