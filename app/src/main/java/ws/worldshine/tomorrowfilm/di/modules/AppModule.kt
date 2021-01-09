package ws.worldshine.tomorrowfilm.di.modules

import dagger.Module
import dagger.Provides
import ws.worldshine.tomorrowfilm.utils.Extra
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideSortBy() = Extra.SORT_BY_POPULARITY

    @Singleton
    @Provides
    fun provideVoteCount() = Extra.VOTE_COUNT_GTE

}
