package com.example.mymovies.di.component

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.example.mymovies.di.NetworkSubComponentsModule
import com.example.mymovies.di.module.AppModule
import com.example.mymovies.di.module.NetworkModule
import com.example.mymovies.di.module.RoomModule
import com.example.mymovies.di.module.ViewModelBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
@Component(modules = [
    AppModule::class,
    ViewModelBuilder::class,
    RoomModule::class,
    FragmentSubComponentsModule::class,
    // NetworkModule::class
   NetworkSubComponentsModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun firstFragmentComponent(): FirstFragmentComponent.Factory
    fun favoriteFragmentComponent(): FavoriteFragmentComponent.Factory
    fun networkBuilder(): NetworkComponent.Builder
}

@Module(
        subcomponents = [
            FirstFragmentComponent::class,
            FavoriteFragmentComponent::class,
        ]
)
object FragmentSubComponentsModule