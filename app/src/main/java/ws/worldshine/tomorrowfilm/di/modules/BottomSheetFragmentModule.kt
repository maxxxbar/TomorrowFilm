package ws.worldshine.tomorrowfilm.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ws.worldshine.tomorrowfilm.di.factories.ViewModelBuilder
import ws.worldshine.tomorrowfilm.di.factories.ViewModelKey
import ws.worldshine.tomorrowfilm.ui.bottomsheet.FiltersBottomSheetFragment
import ws.worldshine.tomorrowfilm.ui.bottomsheet.FiltersBottomSheetViewModel

@Module
abstract class BottomSheetFragmentModule {
    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun filtersBottomSheetFragment(): FiltersBottomSheetFragment

    @Binds
    @IntoMap
    @ViewModelKey(FiltersBottomSheetViewModel::class)
    abstract fun bindViewModel(viewModel: FiltersBottomSheetViewModel): ViewModel
}
