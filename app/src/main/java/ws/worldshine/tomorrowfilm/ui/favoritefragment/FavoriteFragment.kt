package ws.worldshine.tomorrowfilm.ui.favoritefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.android.support.DaggerFragment
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import ws.worldshine.tomorrowfilm.R
import ws.worldshine.tomorrowfilm.adapters.FavoriteAdapter
import ws.worldshine.tomorrowfilm.databinding.FavoriteFragmentBinding
import ws.worldshine.tomorrowfilm.utils.findNavController
import javax.inject.Inject

class FavoriteFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<FavoriteViewModel> { viewModelFactory }
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private val favoriteAdapter = FavoriteAdapter()
    private lateinit var flexboxLayoutManager: FlexboxLayoutManager
    private lateinit var recyclerView: RecyclerView

    @ExperimentalPagingApi
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        setupFlexLayoutManager()
        setupRecyclerView()

        return binding.root
    }

    private fun setupFlexLayoutManager() {
        flexboxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
            flexDirection = FlexDirection.ROW
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.rvFavorite.apply {
            applySystemWindowInsetsToPadding(top = true)
            adapter = favoriteAdapter
            layoutManager = flexboxLayoutManager
            viewModel.getFavoriteMovies().observe(viewLifecycleOwner) {
                favoriteAdapter.submitData(lifecycle, it)
            }
        }
        favoriteAdapter.setOnFavoriteMovieClickListener {
            findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment, viewModel.setFavoriteMovieForDetailFragment(it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}