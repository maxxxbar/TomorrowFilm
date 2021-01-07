package com.example.mymovies.datasource.sorting

import com.example.mymovies.entries.discover.Sorting

interface SortingDataSource {
    fun updateSorting(sortBy: Sorting)
    fun getSorting(): String
}