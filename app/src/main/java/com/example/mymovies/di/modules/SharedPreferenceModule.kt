package com.example.mymovies.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule {

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context, fileName: String): SharedPreferences {
        return context.getSharedPreferences("my.movies_prefernerce", Context.MODE_PRIVATE)
    }

}