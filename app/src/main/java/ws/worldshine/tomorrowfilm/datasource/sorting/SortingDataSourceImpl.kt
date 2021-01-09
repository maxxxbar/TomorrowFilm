package ws.worldshine.tomorrowfilm.datasource.sorting

import android.content.SharedPreferences
import ws.worldshine.tomorrowfilm.discover.SORTING_KEY
import ws.worldshine.tomorrowfilm.discover.Sorting

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