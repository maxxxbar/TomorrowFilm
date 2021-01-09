package ws.worldshine.tomorrowfilm.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import ws.worldshine.tomorrowfilm.App
import ws.worldshine.tomorrowfilm.di.modules.*
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    RoomModule::class,
    NetworkModule::class,
    SharedPreferenceModule::class,
    FirstFragmentModule::class,
    FavoriteFragmentModule::class,
    BottomSheetFragmentModule::class,
    DetailFragmentModule::class,
])
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}
