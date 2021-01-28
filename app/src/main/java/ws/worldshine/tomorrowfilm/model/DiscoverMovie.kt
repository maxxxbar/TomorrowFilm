package ws.worldshine.tomorrowfilm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DiscoverMovie(

        @field:SerializedName("page")
        val page: Int? = null,

        @field:SerializedName("total_pages")
        val totalPages: Int? = null,

        @field:SerializedName("results")
        val results: List<DiscoverMovieResultsItem>? = null,

        @field:SerializedName("total_results")
        val totalResults: Int? = null
)

@Parcelize
@Entity(tableName = "discover_movies")
data class DiscoverMovieResultsItem(

        @PrimaryKey(autoGenerate = true)
        override val uniqueId: Int,

        @field:SerializedName("id")
        override val id: Int,

        @field:SerializedName("overview")
        override val overview: String? = null,

        @field:SerializedName("original_language")
        override val originalLanguage: String? = null,

        @field:SerializedName("original_title")
        override val originalTitle: String? = null,

        @field:SerializedName("video")
        override val video: Boolean? = null,

        @field:SerializedName("title")
        override val title: String? = null,

/*		@field:SerializedName("genre_ids")
		val genreIds: List<Int?>? = null,*/

        @field:SerializedName("poster_path")
        override val posterPath: String? = null,

        @field:SerializedName("backdrop_path")
        override val backdropPath: String? = null,

        @field:SerializedName("release_date")
        override val releaseDate: String? = null,

        @field:SerializedName("popularity")
        override val popularity: Double? = null,

        @field:SerializedName("vote_average")
        override val voteAverage: Float? = null,

        @field:SerializedName("adult")
        override val adult: Boolean? = null,

        @field:SerializedName("vote_count")
        override val voteCount: Float? = null
) : DiscoverMovieItem()
