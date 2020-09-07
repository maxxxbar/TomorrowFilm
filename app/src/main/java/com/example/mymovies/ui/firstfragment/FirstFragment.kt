package com.example.mymovies.ui.firstfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.adapters.MovieAdapter
import com.example.mymovies.databinding.FirstFragmentBinding

class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private lateinit var viewModel: FirstFragmentViewModel
    private lateinit var binding: FirstFragmentBinding
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.first_fragment, container, false)
        recyclerView = binding.recyclerViewPosters
        recyclerView.layoutManager = gridLayoutManager
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = MovieAdapter()
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(FirstFragmentViewModel::class.java)
        recyclerView.adapter = adapter
        viewModel.pagedListLiveData.observe(viewLifecycleOwner, { adapter.submitList(it) })
    }
}


