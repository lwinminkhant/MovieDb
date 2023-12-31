package com.lmkhant.moviedb.data.network

import com.lmkhant.moviedb.domain.model.credits.Credit
import com.lmkhant.moviedb.domain.model.genre.GenreList
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.domain.model.movie.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(): Response<MovieList>

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): Response<MovieList>

    @GET("/3/search/movie")
    suspend fun searchMoviesByTerm(
        @Query("query") searchTerm: String,
        @Query("language") language: String = "en_US"
    ): Response<MovieList>
    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en_US",
    ): Response<Movie>

    @GET("/3/genre/movie/list")
    suspend fun getGenre(): Response<GenreList>

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en_US",
    ):Response<Credit>

}