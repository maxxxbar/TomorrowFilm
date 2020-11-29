package com.example.mymovies.ui.favoritefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.adapters.FavoriteAdapter
import com.example.mymovies.databinding.FavoriteFragmentBinding
import com.example.mymovies.utils.findNavController
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

class FavoriteFragment : Fragment() {
    private lateinit var viewModel: FavoriteViewModel
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private val favoriteAdapter = FavoriteAdapter()
    private lateinit var flexboxLayoutManager: FlexboxLayoutManager
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(FavoriteViewModel::class.java)
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