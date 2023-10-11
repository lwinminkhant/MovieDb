package com.lmkhant.moviedb.data.repository.datasourceimpl


import com.lmkhant.moviedb.data.network.NetworkApi
import com.lmkhant.moviedb.data.repository.datasource.MovieRemoteDatasource
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.domain.model.movie.MovieList
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val networkApi: NetworkApi
) : MovieRemoteDatasource {
    override suspend fun getPopularMovies(): Response<MovieList> = networkApi.getPopularMovies()
    override suspend fun getUpComingMovies(): Response<MovieList> = networkApi.getUpcomingMovies()
    override suspend fun searchMoviesByTerm(term: String): Response<MovieList> =
        networkApi.searchMoviesByTerm(term)

    override suspend fun getMovie(id: Int): Response<Movie> =
        networkApi.getMovie(id)

}

