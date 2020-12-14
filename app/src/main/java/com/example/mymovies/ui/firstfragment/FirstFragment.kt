package com.example.mymovies.ui.firstfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.App
import com.example.mymovies.R
import com.example.mymovies.adapters.LoadStateAdapter
import com.example.mymovies.adapters.MovieAdapter
import com.example.mymovies.databinding.FirstFragmentBinding
import com.example.mymovies.utils.Extra
import com.example.mymovies.utils.findNavController
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
class FirstFragment : Fragment(R.layout.first_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<FirstFragmentViewModel> { viewModelFactory }

    private lateinit var binding: FirstFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var flexBoxLayoutManager: FlexboxLayoutManager

    private val TAG = javaClass.simpleName
    private val movieAdapter = MovieAdapter()
    private var getMoviesJob: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.firstFragmentComponent().create()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(FirstFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FirstFragmentBinding.inflate(layoutInflater)
        setupFlexLayoutManager()
        setupRecyclerView()
        bottomSheetDialog()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        getMovies()
    }

    private fun getMovies() {
        getMoviesJob?.cancel()
        getMoviesJob = lifecycleScope.launch {
            viewModel.getMoviesAsLiveData().observe(viewLifecycleOwner) {
                movieAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewPosters.apply {
            adapter = movieAdapter
            applySystemWindowInsetsToPadding(top = true)
            layoutManager = flexBoxLayoutManager
        }
    }

    private fun setupFlexLayoutManager() {
        flexBoxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
            flexDirection = FlexDirection.ROW
        }
    }

    private fun initAdapter() {
        binding.retryButton.setOnClickListener { movieAdapter.retry() }
        movieAdapter.setOnFilmClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_detailFragment, viewModel.setFilmForDetailFragment(it))
        }
        recyclerView.adapter = movieAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { movieAdapter.retry() }
        )
        movieAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        movieAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
        }
    }


    fun bottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_content)
        bottomSheetDialog.findViewById<Button>(R.id.btn_close)?.apply {
            setOnClickListener {
                bottomSheetDialog.dismiss()
            }
        }
        val radioGroup = bottomSheetDialog.findViewById<RadioGroup>(R.id.rg_filter)
        val bottomSheetInternal: View = bottomSheetDialog.findViewById(R.id.design_bottom_sheet)!!
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetInternal)
        //  BottomSheetBehavior.from(bottomSheetInternal).peekHeight = 500
        bottomSheetBehavior.setUpdateImportantForAccessibilityOnSiblings(true)
        var sortBy: String = Extra.SORT_BY_POPULARITY
        radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            val buttonPopularity = group.findViewById<RadioButton>(R.id.rb_popularity_filter)
            val buttonVoteAvg = group.findViewById<RadioButton>(R.id.rb_vote_avg_filter)
            when (checkedId) {
                R.id.rb_popularity_filter -> {
                    bottomSheetDialog.findViewById<LinearLayout>(R.id.ll_vote_avg_filter)!!.background = null
                    bottomSheetDialog.findViewById<LinearLayout>(R.id.ll_popularity_filter)!!.background =
                            ContextCompat.getDrawable(group.context, R.color.ll_filter_actived_background_color)
                    buttonPopularity.background = ContextCompat.getDrawable(group.context, R.color.ll_filter_actived_background_color)
                    buttonVoteAvg.background = null
                    sortBy = Extra.SORT_BY_POPULARITY
                }
                R.id.rb_vote_avg_filter -> {
                    bottomSheetDialog.findViewById<LinearLayout>(R.id.ll_popularity_filter)!!.background = null
                    bottomSheetDialog.findViewById<LinearLayout>(R.id.ll_vote_avg_filter)!!.background =
                            ContextCompat.getDrawable(group.context, R.color.ll_filter_actived_background_color)
                    buttonVoteAvg.background = ContextCompat.getDrawable(group.context, R.color.ll_filter_actived_background_color)
                    buttonPopularity.background = null
                    sortBy = Extra.SORT_BY_VOTE_AVERAGE
                }
            }
        }

        binding.bsFilter.setOnClickListener {
            bottomSheetDialog.show()
            bottomSheetDialog.findViewById<TextView>(R.id.b_on_bottom_sheet)?.setOnClickListener {
                viewModel.resetRepository(sortBy)
                getMovies()
                bottomSheetDialog.dismiss()
            }
        }

    }
}


