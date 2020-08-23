package com.example.mymovies.ui.mainfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.mymovies.R;
import com.example.mymovies.databinding.MainFragmentBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragmentFragment extends Fragment {

    private MainFragmentViewModel mViewModel;
    private MainFragmentBinding binding;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    public static MainFragmentFragment newInstance() {
        return new MainFragmentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
    }


}