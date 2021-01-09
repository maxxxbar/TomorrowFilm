package ws.worldshine.tomorrowfilm.model

import android.os.Parcelable
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
        override val overview: String,

        @field:SerializedName("original_language")
        override val originalLanguage: String,

        @field:SerializedName("original_title")
        override val originalTitle: String,

        @field:SerializedName("video")
        override val video: Boolean,

        @field:SerializedName("title")
        override val title: String,

/*		@field:SerializedName("genre_ids")
		val genreIds: List<Int?>? = null,*/

        @field:SerializedName("poster_path")
        override val posterPath: String,

        @field:SerializedName("backdrop_path")
        override val backdropPath: String,

        @field:SerializedName("release_date")
        override val releaseDate: String,

        @field:SerializedName("popularity")
        override val popularity: Double,

        @field:SerializedName("vote_average")
        override val voteAverage: Float,

        @field:SerializedName("adult")
        override val adult: Boolean,

        @field:SerializedName("vote_count")
        override val voteCount: Float
) : DiscoverMovieItem(), Parcelable