package ws.worldshine.tomorrowfilm.model

import kotlin.reflect.full.memberProperties


abstract class DiscoverMovieItem {
    abstract val uniqueId: Int
    abstract val id: Int
    abstract val overview: String
    abstract val originalLanguage: String
    abstract val originalTitle: String
    abstract val video: Boolean
    abstract val title: String
    abstract val posterPath: String
    abstract val backdropPath: String
    abstract val releaseDate: String
    abstract val popularity: Double
    abstract val voteAverage: Float
    abstract val adult: Boolean
    abstract val voteCount: Float
}

fun DiscoverMovieResultsItem.toFavoriteMovies() = with(::FavoriteMovies) {
    val properties = DiscoverMovieResultsItem::class.memberProperties.associateBy { it.name }
    callBy(args = parameters.associateWith {
        when (it.name) {
            "uniqueId" -> uniqueId
            "id" -> id
            "overview" -> overview
            "originalLanguage" -> originalLanguage
            "originalTitle" -> originalTitle
            "video" -> video
            "title" -> title
            "posterPath" -> posterPath
            "backdropPath" -> backdropPath
            "releaseDate" -> releaseDate
            "popularity" -> popularity
            "voteAverage" -> voteAverage
            "adult" -> adult
            "voteCount" -> voteCount
            else -> properties[it.name]?.get(this@toFavoriteMovies)
        }
    })
}

fun DiscoverMovieItem.toFavoriteMovies() = with(::FavoriteMovies) {
    val properties = DiscoverMovieItem::class.memberProperties.associateBy { it.name }
    callBy(args = parameters.associateWith {
        when (it.name) {
            "uniqueId" -> uniqueId
            "id" -> id
            "overview" -> overview
            "originalLanguage" -> originalLanguage
            "originalTitle" -> originalTitle
            "video" -> video
            "title" -> title
            "posterPath" -> posterPath
            "backdropPath" -> backdropPath
            "releaseDate" -> releaseDate
            "popularity" -> popularity
            "voteAverage" -> voteAverage
            "adult" -> adult
            "voteCount" -> voteCount
            else -> properties[it.name]?.get(this@toFavoriteMovies)
        }
    })
}

fun DiscoverMovieItem.toDiscoverMovieResultsItem() = with(::DiscoverMovieResultsItem) {
    val properties = DiscoverMovieItem::class.memberProperties.associateBy { it.name }
    callBy(args = parameters.associateWith {
        when (it.name) {
            "uniqueId" -> uniqueId
            "id" -> id
            "overview" -> overview
            "originalLanguage" -> originalLanguage
            "originalTitle" -> originalTitle
            "video" -> video
            "title" -> title
            "posterPath" -> posterPath
            "backdropPath" -> backdropPath
            "releaseDate" -> releaseDate
            "popularity" -> popularity
            "voteAverage" -> voteAverage
            "adult" -> adult
            "voteCount" -> voteCount
            else -> properties[it.name]?.get(this@toDiscoverMovieResultsItem)
        }
    })
}