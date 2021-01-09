package ws.worldshine.tomorrowfilm.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ws.worldshine.tomorrowfilm.di.factories.ViewModelBuilder
import ws.worldshine.tomorrowfilm.di.factories.ViewModelKey
import ws.worldshine.tomorrowfilm.ui.firstfragment.FirstFragment
import ws.worldshine.tomorrowfilm.ui.firstfragment.FirstFragmentViewModel

@Module
abstract class FirstFragmentModule {

    @ExperimentalPagingApi
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun firstFragment(): FirstFragment

    @ExperimentalPagingApi
    @Binds
    @IntoMap
    @ViewModelKey(FirstFragmentViewModel::class)
    abstract fun bindViewModel(viewModel: FirstFragmentViewModel): ViewModel
}