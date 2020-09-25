package com.example.mymovies.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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

@Entity(tableName = "discover_movies")
data class DiscoverMovieResultsItem(

		@PrimaryKey @field:SerializedName("id")
		val id: Int,

		@field:SerializedName("overview")
		val overview: String? = null,

		@field:SerializedName("original_language")
		val originalLanguage: String? = null,

		@field:SerializedName("original_title")
		val originalTitle: String? = null,

		@field:SerializedName("video")
		val video: Boolean? = null,

		@field:SerializedName("title")
		val title: String? = null,

		@field:SerializedName("genre_ids")
		val genreIds: List<Int?>? = null,

		@field:SerializedName("poster_path")
		val posterPath: String? = null,

		@field:SerializedName("backdrop_path")
		val backdropPath: String? = null,

		@field:SerializedName("release_date")
		val releaseDate: String? = null,

		@field:SerializedName("popularity")
		val popularity: Double? = null,

		@field:SerializedName("vote_average")
		val voteAverage: Float? = null,

		@field:SerializedName("adult")
		val adult: Boolean? = null,

		@field:SerializedName("vote_count")
		val voteCount: Float? = null
)
