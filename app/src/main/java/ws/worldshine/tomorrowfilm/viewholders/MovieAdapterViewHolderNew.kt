package ws.worldshine.tomorrowfilm.viewholders

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignSelf
import com.google.android.flexbox.FlexboxLayoutManager
import ws.worldshine.tomorrowfilm.databinding.MovieItemBinding
import ws.worldshine.tomorrowfilm.utils.loadImageWithGlide

class MovieAdapterViewHolderNew(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("WrongConstant")
    fun bind(imgSrc: String) {
        val lp = binding.imageViewSmallPoster.layoutParams
        if (lp is FlexboxLayoutManager.LayoutParams) {
            lp.apply {
                flexGrow = 1f
                alignSelf = AlignSelf.FLEX_END
                flexShrink = 1f
            }
        }
        binding.imageViewSmallPoster.loadImageWithGlide(imgSrc)
    }
}