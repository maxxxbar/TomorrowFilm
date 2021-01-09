package ws.worldshine.tomorrowfilm.ui.detailfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ws.worldshine.tomorrowfilm.databinding.FragmentDetailDescriptionBinding
import ws.worldshine.tomorrowfilm.model.DiscoverMovieItem
import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem
import ws.worldshine.tomorrowfilm.model.toDiscoverMovieResultsItem
import javax.inject.Inject

class DetailFragmentDescription : DaggerFragment() {

    /*ViewModel*/
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<DetailFragmentViewModel> { viewModelFactory }

    /*Binding*/
    private var _binding: FragmentDetailDescriptionBinding? = null
    private val binding get() = _binding!!

    private var job: Job? = null
    private val TAG = javaClass.simpleName
    private var cacheItem: DiscoverMovieItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cacheItem = savedInstanceState?.getParcelable<DiscoverMovieResultsItem>("KEY")
        Log.d(TAG, "onCreate: $cacheItem")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailDescriptionBinding.inflate(layoutInflater, container, false)
        getMovieFromDatabase()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        bindingDescription()
    }


    private fun getMovieFromDatabase() {
        job?.cancel()
        job = lifecycleScope.launch {
            if (cacheItem == null) {
                arguments?.let { bundle ->
                    cacheItem = viewModel.getMovie(bundle.getInt(DetailFragment.BUNDLE_MOVIE_KEY_AS_INT))
                    bindingDescription()
                }
            }
        }
    }

    private fun bindingDescription() {
        binding.tvTitle.text = cacheItem?.title
        binding.tvOriginalTitle.text = cacheItem?.originalTitle
        binding.tvTitleRating.text = cacheItem?.voteAverage.toString()
        binding.tvReleaseDate.text = cacheItem?.releaseDate
        binding.tvDescription.text = cacheItem?.overview
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("KEY", cacheItem?.toDiscoverMovieResultsItem())
    }

    private fun clearCache() {
        arguments?.clear()
        cacheItem = null
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        clearCache()
    }
}



