package ws.worldshine.tomorrowfilm.ui.firstfragment

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ws.worldshine.tomorrowfilm.R
import ws.worldshine.tomorrowfilm.adapters.LoadStateAdapter
import ws.worldshine.tomorrowfilm.adapters.MovieAdapter
import ws.worldshine.tomorrowfilm.databinding.FirstFragmentBinding
import ws.worldshine.tomorrowfilm.discover.SORTING_KEY
import ws.worldshine.tomorrowfilm.utils.findNavController
import javax.inject.Inject


@ExperimentalPagingApi
class FirstFragment : DaggerFragment(R.layout.first_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<FirstFragmentViewModel> { viewModelFactory }

    private lateinit var binding: FirstFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private val TAG = javaClass.simpleName
    private val movieAdapter = MovieAdapter()
    private var job: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBackFromBottomSheet()
        getMovies()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FirstFragmentBinding.inflate(layoutInflater)
        initialSetupGridLayoutManager()
        initialSetupRecyclerView()
        bottomSheetDialog()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetupAdapter()

    }

    private fun initialSetupGridLayoutManager() {
        val orientationPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        val spanCount = if (orientationPortrait) 2 else 3
        gridLayoutManager = GridLayoutManager(requireContext(), spanCount).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = movieAdapter.getItemViewType(position)
                    return if (orientationPortrait) {
                        if (viewType == 1) 2 else 1
                    } else {
                        if (viewType == 1) 3 else 1
                    }
                }

            }
        }
    }

    private fun callBackFromBottomSheet() {
        setFragmentResultListener(SORTING_KEY) { requestKey: String, bundle: Bundle ->
            getMovies()
        }
    }

    private fun getMovies() {
        job?.cancel()
        job = lifecycleScope.launch() {
            viewModel.getMoviesAsLiveData().collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun initialSetupRecyclerView() {
        recyclerView = binding.recyclerViewPosters.apply {
            adapter = movieAdapter
            applySystemWindowInsetsToPadding(top = true)
            layoutManager = gridLayoutManager
        }
    }

    private fun initialSetupAdapter() {
        binding.retryButton.setOnClickListener { movieAdapter.retry() }
        movieAdapter.setOnFilmClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_detailFragment, viewModel.setFilmForDetailFragment(it))
        }
        recyclerView.adapter = movieAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { movieAdapter.retry() }
        )
        movieAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.refresh is LoadState.Error && movieAdapter.itemCount == 0

        }
    }

    private fun MovieAdapter.withLoadStateFooter2(
            footer: androidx.paging.LoadStateAdapter<*>
    ): ConcatAdapter {
        movieAdapter.addLoadStateListener { loadStates ->
            footer.loadState = when (loadStates.refresh) {
                is LoadState.NotLoading -> loadStates.append
                else -> loadStates.refresh
            }

        }
        return ConcatAdapter(this, footer)
    }

    private fun bottomSheetDialog() {
        binding.bsFilter.setOnClickListener {
            findNavController().navigate(R.id.itemListDialogFragment)
        }
    }

}


