package ws.worldshine.tomorrowfilm.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ws.worldshine.tomorrowfilm.di.factories.ViewModelBuilder
import ws.worldshine.tomorrowfilm.di.factories.ViewModelKey
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragment
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragmentDescription
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragmentTrailers
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragmentViewModel

@Module
abstract class DetailFragmentModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun detailFragment(): DetailFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun detailFragmentDescription(): DetailFragmentDescription

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun detailFragmentTrailers(): DetailFragmentTrailers

    @Binds
    @IntoMap
    @ViewModelKey(DetailFragmentViewModel::class)
    abstract fun bindViewModel(viewModel: DetailFragmentViewModel): ViewModel
}