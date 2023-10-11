package com.lmkhant.moviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lmkhant.moviedb.domain.model.movie.Movie


@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "movie_db"
    }
}