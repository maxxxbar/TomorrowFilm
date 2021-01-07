package com.example.mymovies.di.modules

import androidx.lifecycle.ViewModel
import com.example.mymovies.di.factories.ViewModelBuilder
import com.example.mymovies.di.factories.ViewModelKey
import com.example.mymovies.ui.bottomsheet.FiltersBottomSheetFragment
import com.example.mymovies.ui.bottomsheet.FiltersBottomSheetViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class BottomSheetFragmentModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun filtersBottomSheetFragment(): FiltersBottomSheetFragment

    @Binds
    @IntoMap
    @ViewModelKey(FiltersBottomSheetViewModel::class)
    abstract fun bindViewModel(viewModel: FiltersBottomSheetViewModel): ViewModel
}
