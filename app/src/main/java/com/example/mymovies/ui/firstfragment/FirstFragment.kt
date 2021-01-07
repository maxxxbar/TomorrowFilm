package com.example.mymovies.ui.firstfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.adapters.LoadStateAdapter
import com.example.mymovies.adapters.MovieAdapter
import com.example.mymovies.databinding.FirstFragmentBinding
import com.example.mymovies.entries.discover.SORTING_KEY
import com.example.mymovies.entries.discover.Sorting
import com.example.mymovies.ui.bottomsheet.FiltersBottomSheetFragment
import com.example.mymovies.utils.findNavController
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.DaggerFragment
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalPagingApi
class FirstFragment : DaggerFragment(R.layout.first_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<FirstFragmentViewModel> { viewModelFactory }

    private lateinit var binding: FirstFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var flexBoxLayoutManager: FlexboxLayoutManager
    var i = 1
    private val TAG = javaClass.simpleName
    private val movieAdapter = MovieAdapter()
    private var job: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        qweqwe()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FirstFragmentBinding.inflate(layoutInflater)
        initialSetupFlexLayoutManager()
        initialSetupRecyclerView()
        bottomSheetDialog()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialSetupAdapter()
        getMovies()

    }

    private fun qweqwe() {
        setFragmentResultListener(SORTING_KEY) { requestKey: String, bundle: Bundle ->
/*            val sortBy = bundle.getString(FiltersBottomSheetFragment.SORT_BY_KEY)
                    ?: Sorting.POPULARITY.sortBy
            Toast.makeText(requireContext(), "RequestKey : $requestKey BundleString $sortBy", Toast.LENGTH_SHORT).show()*/
            getMovies()
        }
    }

    private fun getMovies() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getMoviesAsLiveData().observe(viewLifecycleOwner) {
                movieAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun initialSetupRecyclerView() {
        recyclerView = binding.recyclerViewPosters.apply {
            adapter = movieAdapter
            applySystemWindowInsetsToPadding(top = true)
            layoutManager = flexBoxLayoutManager
        }
    }

    private fun initialSetupFlexLayoutManager() {
        flexBoxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
            flexDirection = FlexDirection.ROW
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
        movieAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        movieAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.refresh is LoadState.Error
        }
    }

    private fun bottomSheetDialog() {
        binding.bsFilter.setOnClickListener {
            findNavController().navigate(R.id.itemListDialogFragment)
        }
    }

}


