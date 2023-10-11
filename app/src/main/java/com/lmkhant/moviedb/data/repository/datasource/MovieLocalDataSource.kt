package com.lmkhant.moviedb.data.repository.datasource

import com.lmkhant.moviedb.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow


interface MovieLocalDataSource {
    fun getPopularMovies(): Flow<List<Movie>>
    fun getUpComingMovies(): Flow<List<Movie>>

    fun getAllMovies(): Flow<List<Movie>>

    fun getMovie(id: Int): Flow<Movie>

    fun getAllFavouriteMovies(): Flow<List<Movie>>
    suspend fun saveMovies(movieLists: List<Movie>)
    suspend fun saveMovie(movie: Movie)
    suspend fun clearAll()
    suspend fun updateMovie(movie: Movie)
}