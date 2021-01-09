package ws.worldshine.tomorrowfilm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_remote_keys")
data class RemoteKeys(
        @PrimaryKey val movieId: Int,
        val prevKey: Int?,
        val nextKey: Int?
)