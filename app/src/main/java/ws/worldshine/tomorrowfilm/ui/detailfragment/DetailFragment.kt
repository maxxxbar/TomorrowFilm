package ws.worldshine.tomorrowfilm.ui.detailfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.withTransaction
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ws.worldshine.tomorrowfilm.R
import ws.worldshine.tomorrowfilm.adapters.DetailFragmentTabsAdapter
import ws.worldshine.tomorrowfilm.databinding.FragmentDetailBinding
import ws.worldshine.tomorrowfilm.db.MovieDatabaseNew
import ws.worldshine.tomorrowfilm.model.DiscoverMovieItem
import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem
import ws.worldshine.tomorrowfilm.model.FavoriteMovies
import ws.worldshine.tomorrowfilm.utils.JsonConverter
import ws.worldshine.tomorrowfilm.utils.loadImageWithGlide
import javax.inject.Inject

class DetailFragment : DaggerFragment() {

    /*ViewModel*/
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<DetailFragmentViewModel> { viewModelFactory }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val TAG = javaClass.simpleName
    private var job: Job? = null
    private var canceled = false
    private var cacheItem: DiscoverMovieItem? = null
    private lateinit var tabsAdapter: DetailFragmentTabsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        tabsAdapter = DetailFragmentTabsAdapter(this)
        binding.appbar.applySystemWindowInsetsToPadding(top = true)
        setupToolbar()
        getMovieIdFromFirstFragment()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.detailFragmentTabLayout
        val viewPager = binding.detailFragmentViewPager
        viewPager.adapter = tabsAdapter
        viewPager.offscreenPageLimit = 2
        setTabLayoutMediator(tabLayout, viewPager)
        binding.ivFavorite.setOnClickListener {
            addMovieToFavoriteTable()

        }
    }

    private fun setTabLayoutMediator(tabLayout: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Описание"
                1 -> tab.text = "Трейлеры"
            }
        }.attach()
    }

    //TODO setFragmentResult
    private fun getMovieIdFromFirstFragment() {
        arguments?.let { bundle ->
            val movie = bundle.getString(BUNDLE_MOVIE_AS_JSON)
            movie?.let {
                cacheItem = JsonConverter.converter<DiscoverMovieResultsItem>(it)
                cacheItem?.let { discoverMovieItem ->
                    binding.ivPoster.loadImageWithGlide(discoverMovieItem.posterPath)
                    tabsAdapter.movieId = discoverMovieItem.id
                    setFavoriteIconAfterGetMovieData(discoverMovieItem.id)
                }
            }
        }
    }


    private fun setFavoriteIconAfterGetMovieData(movieId: Int) {
        job?.cancel()
        job = lifecycleScope.launch(Dispatchers.Main) {
            if (viewModel.isFavoriteMovie(movieId)) {
                binding.ivFavorite.setImageResource(R.drawable.favourite_remove)
            } else {
                binding.ivFavorite.setImageResource(R.drawable.favourite_add_to)
            }
        }
    }

    private fun addMovieToFavoriteTable() {
        job?.cancel()
        job = lifecycleScope.launch {
            val db = MovieDatabaseNew.getInstance(requireContext())
            cacheItem?.let {
                if (!viewModel.isFavoriteMovie(it.id)) {
                    binding.ivFavorite.setImageResource(R.drawable.favourite_remove)
                    Toast.makeText(requireContext(), "Add to favorite", Toast.LENGTH_SHORT).show()
                    viewModel.insertFavoriteMovie(it)
                } else {
                    binding.ivFavorite.setImageResource(R.drawable.favourite_add_to)
                    Toast.makeText(requireContext(), "Delete from favorite", Toast.LENGTH_SHORT).show()
                    showSnackBar()
                    db.withTransaction {
                        db.movieDao().deleteFavoriteMovieById(it.id)
                    }
                }
            }
        }
    }

    private fun showSnackBar() {
        Snackbar.make(binding.root.rootView, "Удалено", Snackbar.LENGTH_LONG)
                .setAction("Отменить") {
                    Toast.makeText(requireContext(), "Отменено", Toast.LENGTH_SHORT).show()
                }
                .show()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.title = getString(R.string.empty_string)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        toolbar.showOverflowMenu()
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true).let { handleCollapsedToolbarTitle() }

    }

    private fun handleCollapsedToolbarTitle() {
        binding.appbar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                // verify if the toolbar is completely collapsed and set the movie name as the title
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbar.title = cacheItem?.title
                            ?: resources.getString(R.string.app_name)
                    isShow = true
                } else if (isShow) {
                    // display an empty string when toolbar is expanded
                    binding.collapsingToolbar.title = getString(R.string.empty_string)
                    isShow = false
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {

        private const val CANCELED_DURATION_LONG = 2750 //SnackbarManager.LONG_DURATION_MS
        private const val BUNDLE_MOVIE_AS_JSON = "MOVIE_JSON_KEY"
        const val BUNDLE_MOVIE_KEY_AS_INT = "MOVIE_INT_KEY"

        fun setMovieBundle(movie: DiscoverMovieResultsItem): Bundle {
            val bundle = Bundle()
            bundle.putString(BUNDLE_MOVIE_AS_JSON, JsonConverter.converter(movie))
            return bundle
        }

        fun setMovieBundle(movie: FavoriteMovies): Bundle {
            val bundle = Bundle()
            bundle.putString(BUNDLE_MOVIE_AS_JSON, JsonConverter.converter(movie))
            return bundle
        }
    }

}

