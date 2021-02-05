package ws.worldshine.tomorrowfilm.viewholders

import androidx.recyclerview.widget.RecyclerView
import ws.worldshine.tomorrowfilm.databinding.MovieItemBinding
import ws.worldshine.tomorrowfilm.utils.loadImageWithGlide

class MovieAdapterViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(imgSrc: String) {
        binding.imageViewSmallPoster.loadImageWithGlide(imgSrc)
    }
}