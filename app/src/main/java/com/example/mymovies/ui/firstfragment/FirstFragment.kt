package com.example.mymovies.ui.firstfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.adapters.MovieAdapterNew
import com.example.mymovies.databinding.FirstFragmentBinding
import com.google.gson.Gson

class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private lateinit var navController: NavController
    private lateinit var viewModel: FirstFragmentViewModel
    private lateinit var binding: FirstFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private val TAG = javaClass.simpleName


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.first_fragment, container, false)
        recyclerView = binding.recyclerViewPosters
        recyclerView.layoutManager = gridLayoutManager
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = MovieAdapterNew()
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        viewModel = ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(FirstFragmentViewModel::class.java)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)
        recyclerView.adapter = adapter
        viewModel.pagedListLiveData.observe(viewLifecycleOwner, { adapter.submitList(it) })
        adapter.setOnFilmClickListener {
            val bundle = Bundle()
            val gson = Gson()
            val s = gson.toJson(it)
            bundle.putString("FILM", s)
            navController.navigate(R.id.detailFragment, bundle)
        }
    }
}


