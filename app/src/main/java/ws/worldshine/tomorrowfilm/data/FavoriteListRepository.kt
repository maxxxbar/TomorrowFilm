package ws.worldshine.tomorrowfilm.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import ws.worldshine.tomorrowfilm.datasource.FavoriteDataSource
import ws.worldshine.tomorrowfilm.model.FavoriteMovies
import javax.inject.Inject

class FavoriteListRepository
@Inject constructor(
        private val favoriteDataSource: FavoriteDataSource
) {
    private val config = PagingConfig(pageSize = 10)
    private val pagingSourceFactory = { favoriteDataSource }
    fun getFavoriteMovies(): LiveData<PagingData<FavoriteMovies>> {
        return Pager(
                config = config,
                pagingSourceFactory = pagingSourceFactory
        ).liveData
    }
}