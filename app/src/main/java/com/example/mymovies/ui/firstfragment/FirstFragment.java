package com.example.mymovies.ui.firstfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.adapters.MovieAdapter;
import com.example.mymovies.databinding.FirstFragmentBinding;

public class FirstFragment extends Fragment {

    private FirstFragmentViewModel mViewModel;
    private FirstFragmentBinding binding;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.first_fragment, container, false);
        recyclerView = binding.recyclerViewPosters;
        gridLayoutManager = new GridLayoutManager(requireActivity().getApplicationContext(), 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(FirstFragmentViewModel.class);
        mViewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), results -> adapter.submitList(results));
    }

}