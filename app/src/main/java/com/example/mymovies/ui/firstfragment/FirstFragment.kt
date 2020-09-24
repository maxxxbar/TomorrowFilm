package com.example.mymovies.ui.firstfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.adapters.LoadStateAdapter
import com.example.mymovies.adapters.MovieAdapterNew
import com.example.mymovies.databinding.FirstFragmentBinding
import com.example.mymovies.entries.discover.moviesnew.DiscoverMovieResultsItem
import com.example.mymovies.utils.findNavController
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private lateinit var navController: NavController
    private lateinit var viewModel: FirstFragmentViewModel
    private lateinit var binding: FirstFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val TAG = javaClass.simpleName
    private val adapter = MovieAdapterNew()
    private var getMoviesJob: Job? = null

    private fun getMovies() {
        getMoviesJob?.cancel()
        getMoviesJob = lifecycleScope.launch {
            viewModel.getMoviesAsLiveData().observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun search() {
        // Make sure we cancel the previous job before creating a new one
        getMoviesJob?.cancel()
        getMoviesJob = lifecycleScope.launch {
            viewModel.searchResult().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.first_fragment, container, false)
        val flexboxLayoutManager = FlexboxLayoutManager(requireContext())
        recyclerView = binding.recyclerViewPosters
        setupRecyclerView(flexboxLayoutManager)
        setupFlexLayoutManager(flexboxLayoutManager)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(FirstFragmentViewModel::class.java)
        initAdapter()
        getMovies()
    }


    private fun setupRecyclerView(flexboxLayoutManager: FlexboxLayoutManager) {
        recyclerView.applySystemWindowInsetsToPadding(top = true)
        recyclerView = binding.recyclerViewPosters
        recyclerView.layoutManager = flexboxLayoutManager
        recyclerView.setHasFixedSize(true)
    }

    private fun setupFlexLayoutManager(flexboxLayoutManager: FlexboxLayoutManager) {
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        flexboxLayoutManager.alignItems = AlignItems.STRETCH
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
    }

    private fun initAdapter() {
        binding.retryButton.setOnClickListener { adapter.retry() }
        adapter.setOnFilmClickListener {
            setFilmFromIntent(it)
        }
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoadStateAdapter { adapter.retry() },
                footer = LoadStateAdapter { adapter.retry() }
        )
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.addLoadStateListener { loadState ->
            binding.recyclerViewPosters.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

    private fun setFilmFromIntent(result: DiscoverMovieResultsItem) {
        val bundle = Bundle()
        val gson = Gson()
        val s = gson.toJson(result)
        bundle.putString("FILM", s)
        findNavController().navigate(R.id.action_firstFragment_to_detailFragment, bundle)
    }

}


