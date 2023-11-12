package com.lmkhant.moviedb.data.repository.datasource

import com.lmkhant.moviedb.domain.model.credits.Credit
import com.lmkhant.moviedb.domain.model.genre.GenreList
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.domain.model.movie.MovieList
import retrofit2.Response

interface MovieRemoteDatasource {
   suspend fun getPopularMovies(): Response<MovieList>
   suspend fun getUpComingMovies(): Response<MovieList>
   suspend fun searchMoviesByTerm(term: String): Response<MovieList>
   suspend fun getMovie(id: Int): Response<Movie>

   suspend fun getGenres(): Response<GenreList>

   suspend fun getMovieCredits(id: Int): Response<Credit>

}