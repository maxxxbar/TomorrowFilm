package ws.worldshine.tomorrowfilm.ui.bottomsheet

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import ws.worldshine.tomorrowfilm.discover.SORTING_KEY
import ws.worldshine.tomorrowfilm.discover.Sorting
import javax.inject.Inject

class FiltersBottomSheetViewModel @Inject constructor(
        val sp: SharedPreferences
) : ViewModel() {
    private val TAG = javaClass.simpleName

    fun getCurrentSortBy(): Sorting? {

        return Sorting.values().find {
            it.sortBy == sp.getString(SORTING_KEY, Sorting.POPULARITY.sortBy)
        }
    }

    fun setSorting(sortBy: String) {
        Log.d(TAG, "setSorting: $sortBy")
        sp.edit().putString(SORTING_KEY, sortBy).apply()
        Log.d(TAG, "setSorting: ${sp.getString(SORTING_KEY, null)}")
    }
}