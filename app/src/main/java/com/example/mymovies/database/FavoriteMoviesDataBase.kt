package com.example.mymovies.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
class FavoriteMoviesDataBase(
        @PrimaryKey(autoGenerate = true) val uniqueId: Int?,
        val id: Int?,
        val voteCount: Int?,
        val title: String?,
        val originalTitle: String?,
        val overview: String?,
        val posterPath: String?,
        val bigPosterPath: String?,
        val backdropPath: String?,
        val voteAverage: Double?,
        val releaseDate: String?
)