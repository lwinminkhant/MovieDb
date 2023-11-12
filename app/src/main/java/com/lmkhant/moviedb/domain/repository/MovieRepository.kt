package com.lmkhant.moviedb.domain.repository

import com.lmkhant.moviedb.domain.model.credits.Credit
import com.lmkhant.moviedb.domain.model.genre.Genre
import com.lmkhant.moviedb.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    suspend fun getPopularMovies(): Flow<List<Movie>>
    suspend fun getUpComingMovies(): Flow<List<Movie>>
    suspend fun getAllFavoriteMovies(): Flow<List<Movie>>
    suspend fun fetchNewMovies()
    suspend fun updateMovie(movie: Movie)
    suspend fun save(movie: Movie)
    suspend fun searchByTerm(term: String): Flow<List<Movie>>
    suspend fun getMovie(id: Int): Flow<Movie>
    suspend fun getGenreList(): Flow<List<Genre>>

    suspend fun getMovieCredits(id: Int) : Credit
}