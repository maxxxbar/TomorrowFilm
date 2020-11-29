package com.example.mymovies.ui.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.withTransaction
import androidx.viewpager2.widget.ViewPager2
import com.example.mymovies.R
import com.example.mymovies.adapters.DetailFragmentTabsAdapter
import com.example.mymovies.databinding.FragmentDetailBinding
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.model.FavoriteMovies
import com.example.mymovies.utils.loadImageWithGlide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.*

class DetailFragment : Fragment() {

    companion object {
        private const val BUNDLE_MOVIE_AS_JSON = "MOVIE_JSON_KEY"
        const val BUNDLE_MOVIE_KEY_AS_INT = "MOVIE_INT_KEY"
        private const val BUNDLE_MOVIE_POSTER_PATH = "MOVIE_POSTER_PATH"
        private const val BUNDLE_MOVIE_MOVIE_TITLE = "MOVIE_TITLE"

        fun setMovieBundle(movie: DiscoverMovieResultsItem): Bundle {
            val bundle = Bundle()
            val gson = Gson()
            val _movie = gson.toJson(movie)
            bundle.putString(BUNDLE_MOVIE_AS_JSON, _movie)
            return bundle
        }

        fun setMovieBundle(movie: FavoriteMovies): Bundle {
            val bundle = Bundle()
            val gson = Gson()
            val _movie = gson.toJson(movie)
            bundle.putString(BUNDLE_MOVIE_AS_JSON, _movie)
            return bundle
        }
    }


    private lateinit var viewModel: DetailFragmentViewModel
    private lateinit var binding: FragmentDetailBinding
    private val TAG = javaClass.simpleName
    private var job: Job? = null
    private var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(DetailFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.appbar.applySystemWindowInsetsToPadding(top = true)
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.detailFragmentTabLayout
        val viewPager = binding.detailFragmentViewPager
        val adapterTabsAdapter = DetailFragmentTabsAdapter(this)
        viewPager.adapter = adapterTabsAdapter
        viewPager.offscreenPageLimit = 2
        getMovieIdFromFirstFragment(adapterTabsAdapter)
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


    private fun getMovieIdFromFirstFragment(adapter: DetailFragmentTabsAdapter) {
        arguments?.let { bundle ->
            val movie = bundle.getString(BUNDLE_MOVIE_AS_JSON)
            movie?.let {
                val gson = Gson()
                val type = object : TypeToken<DiscoverMovieResultsItem>() {}.type
                val result = gson.fromJson<DiscoverMovieResultsItem>(it, type)
                adapter.movieId = result.id
                movieId = result.id
                setFavoriteIconAfterGetMovieData(result.id)
                result.posterPath?.let { binding.ivPoster.loadImageWithGlide(it) }
            }
        }
    }

    private fun setFavoriteIconAfterGetMovieData(movieId: Int) {
        job?.cancel()
        job = lifecycleScope.launch(Dispatchers.Main) {
            if (checkFavoriteMovieInDatabase(movieId)) {
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
            movieId?.let {
                if (!checkFavoriteMovieInDatabase(it)) {
                    binding.ivFavorite.setImageResource(R.drawable.favourite_remove)
                    Toast.makeText(requireContext(), "Add to favorite", Toast.LENGTH_SHORT).show()
                    db.withTransaction {
                        db.movieDao().insertFavoriteMovie(castDiscoverMovieResultItemToFavoriteMovies(db.movieDao().getMovieById(it)))
                    }
                } else {
                    binding.ivFavorite.setImageResource(R.drawable.favourite_add_to)
                    Toast.makeText(requireContext(), "Delete from favorite", Toast.LENGTH_SHORT).show()
                    db.withTransaction {
                        db.movieDao().deleteFavoriteMovieById(it)
                    }
                }
            }
        }
    }

    private fun castDiscoverMovieResultItemToFavoriteMovies(movie: DiscoverMovieResultsItem): FavoriteMovies {
        return FavoriteMovies(
                uniqueId = movie.uniqueId,
                id = movie.id,
                overview = movie.overview,
                originalLanguage = movie.originalLanguage,
                originalTitle = movie.originalTitle,
                video = movie.video,
                title = movie.title,
                posterPath = movie.posterPath,
                backdropPath = movie.backdropPath,
                releaseDate = movie.releaseDate,
                popularity = movie.popularity,
                voteAverage = movie.voteAverage,
                adult = movie.adult,
                voteCount = movie.voteCount
        )
    }

    private suspend fun checkFavoriteMovieInDatabase(movieId: Int): Boolean {
        var result = false
        val waitFor = CoroutineScope(Dispatchers.IO).async {
            val db = MovieDatabaseNew.getInstance(requireContext())
            val idExits: FavoriteMovies? = db.movieDao().getFavoriteMovieById(movieId)
            result = idExits != null
            return@async result
        }
        waitFor.await()
        return result
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
                    val title = arguments?.getString(BUNDLE_MOVIE_MOVIE_TITLE)
                    binding.collapsingToolbar.title = title
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

}

