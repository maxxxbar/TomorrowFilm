package com.example.mymovies.ui.favoritefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.adapters.FavoriteAdapter
import com.example.mymovies.databinding.FavoriteFragmentBinding

class FavoriteFragment : Fragment() {
    private lateinit var viewModel: FavoriteViewModel
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = FavoriteAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(FavoriteViewModel::class.java)
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        val recyclerView = binding.rvFavorite
        recyclerView.adapter = adapter
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}