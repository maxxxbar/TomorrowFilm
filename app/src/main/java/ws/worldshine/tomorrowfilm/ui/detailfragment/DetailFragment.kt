package ws.worldshine.tomorrowfilm.ui.detailfragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerFragment
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ws.worldshine.tomorrowfilm.R
import ws.worldshine.tomorrowfilm.adapters.DetailFragmentTabsAdapter
import ws.worldshine.tomorrowfilm.databinding.FragmentDetailBinding
import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem
import ws.worldshine.tomorrowfilm.model.FavoriteMovies
import ws.worldshine.tomorrowfilm.utils.createSnackBar
import ws.worldshine.tomorrowfilm.utils.loadImageWithGlide
import javax.inject.Inject

class DetailFragment : DaggerFragment() {

    /*ViewModel*/
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<DetailFragmentViewModel> { viewModelFactory }
    /*ViewModel*/

    /*Binding*/
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    /*Binding*/

    private val TAG = javaClass.simpleName
    private lateinit var tabsAdapter: DetailFragmentTabsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        tabsAdapter = DetailFragmentTabsAdapter(this)
        binding.appbar.applySystemWindowInsetsToPadding(left = true, top = true, right = true)
        getMovieFromArguments()
        initialSetupToolbar()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetupTabLayoutMediator()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
    }

    private fun initialSetupTabLayoutMediator() {
        val tabLayout = binding.detailFragmentTabLayout
        val viewPager = binding.detailFragmentViewPager
        viewPager.adapter = tabsAdapter
        viewPager.offscreenPageLimit = 2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.df_tabs_title_description)
                1 -> tab.text = resources.getString(R.string.df_tabs_title_trailers)
            }
        }.attach()
    }

    private fun getMovieFromArguments() {
        arguments?.let { bundle ->
            viewModel.cacheItem = bundle.getParcelable(BUNDLE_MOVIE_AS_JSON)
            viewModel.cacheItem?.let { discoverMovieItem ->
                viewModel.checkInFavorite(discoverMovieItem.id)
                tabsAdapter.movieId = discoverMovieItem.id
                discoverMovieItem.posterPath?.let { binding.ivPoster.loadImageWithGlide(it) }
            }
        }
    }

    private fun handleFavoriteMovieIcon() {
        lifecycleScope.launch {
            viewModel.isFavoriteMovies.collectLatest {
                runCatching {
                    switchFavoriteIcon()
                }
            }
        }
    }

    private suspend fun switchFavoriteIcon() {
        val menuItem: MenuItem = binding.toolbar.menu[0]
        viewModel.isFavoriteMovies.collectLatest {
            if (it) {
                menuItem.icon.setTint(Color.YELLOW)
            } else {
                menuItem.icon?.setTint(Color.WHITE)
            }
        }
    }

    private fun showSnackBar() {
        val snackBarAction: (View) -> Unit = { viewModel.canceled.value = true }
        val snackBar = createSnackBar(binding.root,
                resources.getString(R.string.df_snackBar_title_delete_completed),
                Snackbar.LENGTH_LONG,
                resources.getString(R.string.df_snackBar_action_title_delete_cancel),
                snackBarAction)
        snackBar.show()
    }

    private fun initialSetupToolbar() {
        val toolbar = binding.toolbar
        val activity = activity as AppCompatActivity
        toolbar.title = getString(R.string.empty_string)
        activity.setSupportActionBar(toolbar)
        toolbar.showOverflowMenu()
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true).let {
            handleCollapsedToolbarTitle()
            handleFavoriteMovieIcon()
            toolbarMenuHandler(toolbar)
        }
    }

    private fun toolbarMenuHandler(toolbar: MaterialToolbar) {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_to_favorite -> {
                    viewModel.addMovieToFavoriteTable(showSnackBar = { showSnackBar() })
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }
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
                    binding.collapsingToolbar.title = viewModel.cacheItem?.title
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
        private const val BUNDLE_MOVIE_AS_JSON = "MOVIE_JSON_KEY"
        const val BUNDLE_MOVIE_KEY_AS_INT = "MOVIE_INT_KEY"

        fun setMovieBundle(movie: DiscoverMovieResultsItem): Bundle {
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_MOVIE_AS_JSON, movie)
            return bundle
        }

        fun setMovieBundle(movie: FavoriteMovies): Bundle {
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_MOVIE_AS_JSON, movie)
            return bundle
        }
    }

}
