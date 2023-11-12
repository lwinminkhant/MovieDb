package com.lmkhant.moviedb.data.repository

import com.lmkhant.moviedb.data.network.MovieRequestType
import com.lmkhant.moviedb.data.network.NoConnectivityException
import com.lmkhant.moviedb.data.repository.datasource.MovieLocalDataSource
import com.lmkhant.moviedb.data.repository.datasource.MovieRemoteDatasource
import com.lmkhant.moviedb.domain.model.credits.Credit
import com.lmkhant.moviedb.domain.model.genre.Genre
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.domain.model.movie.MovieList
import com.lmkhant.moviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class MovieRepositoryImpl(
    private val movieRemoteDatasource: MovieRemoteDatasource,
    private val movieLocalDataSource: MovieLocalDataSource,
) : MovieRepository {
    override suspend fun getPopularMovies(): Flow<List<Movie>> {
        return getPopularMoviesFromDB()
    }

    override suspend fun getUpComingMovies(): Flow<List<Movie>> {
        return getUpComingMoviesFromDB()
    }

    override suspend fun getAllFavoriteMovies(): Flow<List<Movie>> {
        return movieLocalDataSource.getAllFavouriteMovies()
    }

    override suspend fun fetchNewMovies() {

        val popularMoviesDB: List<Movie> = movieLocalDataSource.getPopularMovies().first()
        val upComingMoviesDB: List<Movie> = movieLocalDataSource.getUpComingMovies().first()

        movieLocalDataSource.saveMovies(popularMoviesDB.zip(getPopularMoviesFromAPI()) { db, api ->
            api.copy(favourite = db.favourite)
        })
        movieLocalDataSource.saveMovies(upComingMoviesDB.zip(getUpComingMoviesFromAPI()) { db, api ->
            api.copy(favourite = db.favourite)
        })
    }

    private suspend fun getPopularMoviesFromAPI(): List<Movie> {
        return fetchMoviesFromAPI(movieRemoteDatasource::getPopularMovies, MovieRequestType.Popular)
    }

    private suspend fun getUpComingMoviesFromAPI(): List<Movie> {
        return fetchMoviesFromAPI(
            movieRemoteDatasource::getUpComingMovies,
            MovieRequestType.UpComing
        )
    }

    private suspend fun fetchMoviesFromAPI(
        fetchFunction: suspend () -> Response<MovieList>,
        type: MovieRequestType
    ): List<Movie> {

        val movieList: List<Movie> = try {
            val response = fetchFunction.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                val movies = body?.movies
                movies?.mapIndexed { index, movie ->
                    movie.copy(
                        id = movie.id,/*generatePrimaryKey(type.toString(), movie.id)*/
                        type = type.toString(),
                        index = index,
                    )
                } ?: emptyList()
            } else {
                Timber.d("movie response failed to receive")
                emptyList()
            }
        } catch (ex: Exception) {
            emptyList()
        }
        return movieList
    }

    private suspend fun fetchMoviesFromAPI(
        fetchFunction: suspend () -> Response<MovieList>
    ): List<Movie> {


        val movieList: List<Movie> = try {
            val response = fetchFunction.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                val movies = body?.movies
                movies?.mapIndexed { index, movie ->
                    movie.copy(
                        id = movie.id,
                        type = MovieRequestType.Popular.toString(),
                        index = index,
                        backdropPath = if (movie.backdropPath.isNullOrEmpty()) "" else movie.backdropPath,
                        posterPath = if (movie.posterPath.isNullOrEmpty()) "" else movie.posterPath,
                        genreIds = if (movie.genreIds.isNullOrEmpty()) emptyList() else movie.genreIds,
                    )
                } ?: emptyList()
            } else {
                Timber.e("failed to receive from api")
                emptyList()
            }
        } catch (ex: NoConnectivityException) {
            Timber.e("fetch error: $ex")
            emptyList()
        }
        return movieList
    }


    private suspend fun getPopularMoviesFromDB(): Flow<List<Movie>> {
        val movieList: Flow<List<Movie>> = try {
            movieLocalDataSource.getPopularMovies()
        } catch (exception: Exception) {
            flowOf(emptyList())
        }

        if (movieList.first().isEmpty()) {
            movieLocalDataSource.saveMovies(getPopularMoviesFromAPI())
        }
        return movieList
    }


    private suspend fun getUpComingMoviesFromDB(): Flow<List<Movie>> {
        val movieList: Flow<List<Movie>> = try {
            movieLocalDataSource.getUpComingMovies()
        } catch (exception: Exception) {
            flowOf(emptyList())
        }

        if (movieList.first().isEmpty()) {
            movieLocalDataSource.saveMovies(getUpComingMoviesFromAPI())
        }
        return movieList
    }


    override suspend fun getMovie(id: Int): Flow<Movie> {
        val localMovie = movieLocalDataSource.getMovie(id)
        if (localMovie.firstOrNull() == null) {
            getMovieFromApi(id).let {
                movieLocalDataSource.saveMovie(it)
            }
        }
        return localMovie
    }

    private suspend fun getMovieFromApi(id: Int): Movie {
        try {
            val response = movieRemoteDatasource.getMovie(id)

            if (response.isSuccessful) {
                response.body()?.let { movie ->
                    return movie.copy(
                        favourite = false,
                        type = MovieRequestType.Search.toString(),
                        backdropPath = if (movie.backdropPath.isNullOrEmpty()) "" else movie.backdropPath,
                        posterPath = if (movie.posterPath.isNullOrEmpty()) "" else movie.posterPath,
                        genreIds = if (movie.genreIds.isNullOrEmpty()) emptyList() else movie.genreIds,
                    )
                }

                throw IOException("Failed to retrieve movie")
            }


        }catch (e :NoConnectivityException){
            Timber.e(e.message)
        }
        throw IOException("Failed to retrieve movie")
    }

    override suspend fun updateMovie(movie: Movie) {
        movieLocalDataSource.updateMovie(movie)
    }

    override suspend fun save(movie: Movie) {
        movieLocalDataSource.saveMovie(movie)
    }

    override suspend fun searchByTerm(term: String): Flow<List<Movie>> {
        val searchResult = fetchMoviesFromAPI { movieRemoteDatasource.searchMoviesByTerm(term) }
        return flowOf(searchResult)
    }

    override suspend fun getGenreList(): Flow<List<Genre>> {
        return getGenresFormDb()
    }

    private suspend fun getGenresFormDb(): Flow<List<Genre>>{
        val genres = try {
            movieLocalDataSource.getGenres()
        } catch (e: Exception) {
            flowOf(emptyList())
        }
        if (genres.first().isEmpty()) {
            movieLocalDataSource.saveGenres(getGenresFromApi())
        }
        return genres
    }
    private suspend fun getGenresFromApi(): List<Genre> {
        try {
            val response = movieRemoteDatasource.getGenres()

            if (response.isSuccessful) {
                response.body()?.let {
                    return it.genres
                }
            }
        }catch (e: NoConnectivityException){
            Timber.e(e.message)
        }
        return emptyList()
    }

    override suspend fun getMovieCredits(id: Int): Credit {
        val response = movieRemoteDatasource.getMovieCredits(id)
        Timber.d("Response: ${response.body()}")
        if (response.isSuccessful) {
            response.body()?.let {
                Timber.d("Cast ${it.cast.size}")
                return it
            }

        }
        throw IOException("Filled to retrieve credits for $id}")
    }
}