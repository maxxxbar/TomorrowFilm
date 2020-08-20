package com.example.mymovies.ui.secondfragment;

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
import com.example.mymovies.databinding.SecondFragmentBinding;

public class SecondFragment extends Fragment {

    private SecondFragmentViewModel mViewModel;
    private SecondFragmentBinding binding;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.second_fragment, container, false);
        recyclerView = binding.recyclerViewPostersF2;
        gridLayoutManager = new GridLayoutManager(requireActivity().getApplicationContext(), 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(SecondFragmentViewModel.class);
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);
        mViewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), results -> adapter.submitList(results));
    }

}