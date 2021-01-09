package ws.worldshine.tomorrowfilm.di.modules

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ws.worldshine.tomorrowfilm.di.factories.ViewModelBuilder
import ws.worldshine.tomorrowfilm.di.factories.ViewModelKey
import ws.worldshine.tomorrowfilm.ui.favoritefragment.FavoriteFragment
import ws.worldshine.tomorrowfilm.ui.favoritefragment.FavoriteViewModel

@Module
abstract class FavoriteFragmentModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun favoriteFragment(): FavoriteFragment

    @ExperimentalPagingApi
    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindViewModel(viewModel: FavoriteViewModel): ViewModel

}