package ws.worldshine.tomorrowfilm.datasource.sorting

import ws.worldshine.tomorrowfilm.discover.Sorting

interface SortingDataSource {
    fun updateSorting(sortBy: Sorting)
    fun getSorting(): String
}