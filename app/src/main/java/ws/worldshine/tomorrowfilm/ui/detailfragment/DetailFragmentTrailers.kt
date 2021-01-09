package ws.worldshine.tomorrowfilm.ui.detailfragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.launch
import ws.worldshine.tomorrowfilm.adapters.TrailerAdapter
import ws.worldshine.tomorrowfilm.databinding.FragmentDetailTrailersBinding
import ws.worldshine.tomorrowfilm.discover.trailer.Result
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragment.Companion.BUNDLE_MOVIE_KEY_AS_INT
import javax.inject.Inject

class DetailFragmentTrailers : DaggerFragment() {

    private companion object {
        private const val YOUTUBE_MOVIE_URL = "https://www.youtube.com/watch?v="
    }

    /*ViewModel*/
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<DetailFragmentViewModel> { viewModelFactory }

    /*Binding*/
    private var _binding: FragmentDetailTrailersBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private val trailerAdapter = TrailerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailTrailersBinding.inflate(layoutInflater, container, false)
        getMovieFromTrailerAdapter()
        initialSetupRecyclerView()
        return binding.root
    }


    private fun getMovieFromTrailerAdapter() {
        arguments?.let { bundle ->
            bundle.getInt(BUNDLE_MOVIE_KEY_AS_INT).let { movieId ->
                lifecycleScope.launch {
                    setAdapter(viewModel.getTrailers(movieId))
                }
            }
        }
    }

    private fun setAdapter(list: List<Result>) {
        list.isNotEmpty().let {
            trailerAdapter.setTrailerList(list)
            trailerAdapter.setOnClickWatchTrailer { position ->
                val movieUrl = YOUTUBE_MOVIE_URL + list[position].key
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movieUrl))
                startActivity(intent)
            }
        }
    }

    private fun initialSetupRecyclerView() {
        recyclerView = binding.rvFragmentDetailTrailers
        recyclerView.adapter = trailerAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}