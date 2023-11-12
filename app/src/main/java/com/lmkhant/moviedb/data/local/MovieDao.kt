package com.lmkhant.moviedb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lmkhant.moviedb.domain.model.credits.Credit
import com.lmkhant.moviedb.domain.model.genre.Genre
import com.lmkhant.moviedb.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun saveMovies(movieList: List<Movie>)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun saveMovie(movie: Movie)

        @Query("DELETE FROM movies")
        suspend fun deleteAllPopularMovies()

        @Query("SELECT * FROM movies WHERE type = 'Popular' ORDER BY `index`")
        fun getPopularMovies(): Flow<List<Movie>>

        @Query("DELETE FROM movies")
        suspend fun deleteAllUpComingMovies()

        @Query("SELECT * FROM movies WHERE type = 'UpComing' ORDER BY `index`")
        fun getUpComingMovies(): Flow<List<Movie>>

        @Query("SELECT * FROM movies ORDER BY `index`")
        fun getAllMovies(): Flow<List<Movie>>

        @Query("SELECT * FROM movies WHERE favourite=1 ORDER BY `index`")
        fun getAllFavouriteMovies(): Flow<List<Movie>>

        @Query("SELECT * FROM movies WHERE id=:id")
        fun getMovie(id: Int): Flow<Movie>
        @Update
        suspend fun updateMovie(movie: Movie)

        @Update
        suspend fun updateMovies(movieList: List<Movie>)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun saveGenres(genres: List<Genre>)
        @Query("SELECT * FROM genres")
        fun getGenre(): Flow<List<Genre>>

}