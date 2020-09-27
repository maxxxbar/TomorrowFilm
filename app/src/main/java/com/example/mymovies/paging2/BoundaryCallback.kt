package com.example.mymovies.paging2

import androidx.paging.PagedList
import com.example.mymovies.model.DiscoverMovieResultsItem

class BoundaryCallback() : PagedList.BoundaryCallback<DiscoverMovieResultsItem>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
    }

    override fun onItemAtEndLoaded(itemAtEnd: DiscoverMovieResultsItem) {
        super.onItemAtEndLoaded(itemAtEnd)
    }
}