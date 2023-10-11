package com.lmkhant.moviedb.data.repository.datasourceimpl

import com.lmkhant.moviedb.data.local.MovieDao
import com.lmkhant.moviedb.data.repository.datasource.MovieLocalDataSource
import com.lmkhant.moviedb.domain.model.movie.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MovieLocalDataSourceImpl(
    private val movieDao: MovieDao
) :
    MovieLocalDataSource {
    override fun getPopularMovies(): Flow<List<Movie>> = movieDao.getPopularMovies()
    override fun getUpComingMovies(): Flow<List<Movie>> =
        movieDao.getUpComingMovies()

    override suspend fun saveMovies(movieLists: List<Movie>) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.saveMovies(movieLists)
        }
    }

    override suspend fun saveMovie(movie: Movie) {
        movieDao.saveMovie(movie)
    }

    override fun getAllMovies(): Flow<List<Movie>> =
        movieDao.getAllMovies()

    override fun getMovie(id: Int): Flow<Movie> =
        movieDao.getMovie(id)

    override fun getAllFavouriteMovies(): Flow<List<Movie>> =
        movieDao.getAllFavouriteMovies()

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.deleteAllPopularMovies()
            movieDao.deleteAllUpComingMovies()
        }
    }

    override suspend fun updateMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.updateMovie(movie)
        }
    }
}