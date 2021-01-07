package com.example.mymovies.datasource.sorting

import android.content.SharedPreferences
import com.example.mymovies.entries.discover.SORTING_KEY
import com.example.mymovies.entries.discover.Sorting

class SortingDataSourceImpl(
        private val sp: SharedPreferences
) : SortingDataSource {

    override fun updateSorting(sortBy: Sorting) {
        sp.edit().putString(SORTING_KEY, sortBy.sortBy).apply()
    }

    override fun getSorting(): String {
        return sp.getString(SORTING_KEY, Sorting.POPULARITY.sortBy) ?: Sorting.POPULARITY.sortBy
    }
}