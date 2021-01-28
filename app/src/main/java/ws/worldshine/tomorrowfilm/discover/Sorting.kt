package ws.worldshine.tomorrowfilm.discover

const val SORTING_KEY = "SORTINGKEY"

enum class Sorting(val value: String) {
    POPULARITY("popularity.desc"),
    VOTE_AVERAGE("vote_average.desc")
}


