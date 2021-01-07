package com.example.mymovies.di.component

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.App
import com.example.mymovies.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    RoomModule::class,
    NetworkModule::class,
    SharedPreferenceModule::class,
    FirstFragmentModule::class,
    FavoriteFragmentModule::class,
    BottomSheetFragmentModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

}
