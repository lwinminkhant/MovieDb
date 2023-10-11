package com.lmkhant.moviedb.data.repository

import com.google.gson.Gson
import com.lmkhant.moviedb.RetrofitHelper
import com.lmkhant.moviedb.data.network.NetworkApi
import com.lmkhant.moviedb.data.repository.datasource.MovieRemoteDatasource
import com.lmkhant.moviedb.data.repository.datasourceimpl.MovieRemoteDataSourceImpl
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.domain.model.movie.MovieList
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class MovieRepositoryImplTest {
    private lateinit var repository: MovieRemoteDatasource
    private lateinit var networkApi: NetworkApi
    private lateinit var mockWebServer: MockWebServer
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        networkApi = RetrofitHelper.testApiInstance(mockWebServer.url("/").toString())
        repository = MovieRemoteDataSourceImpl(networkApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun `for no users, api must return empty with http code 200`() = runTest {
        val movie1 = Movie(
            adult = false,
            backdropPath = "/backdrop1.jpg",
            genreIds = listOf(28, 12),
            id = 1,
            originalLanguage = "en",
            originalTitle = "Movie One",
            overview = "This is the overview of Movie One.",
            popularity = 123.45,
            posterPath = "/poster1.jpg",
            releaseDate = "2023-08-01",
            title = "Movie One",
            video = false,
            voteAverage = 7.8,
            voteCount = 1000,
            favourite = false,
            type = "popular",
            index = 0
        )

        val movie2 = Movie(
            adult = false,
            backdropPath = "/backdrop2.jpg",
            genreIds = listOf(18, 27),
            id = 2,
            originalLanguage = "en",
            originalTitle = "Movie Two",
            overview = "This is the overview of Movie Two.",
            popularity = 98.76,
            posterPath = "/poster2.jpg",
            releaseDate = "2023-07-15",
            title = "Movie Two",
            video = false,
            voteAverage = 6.5,
            voteCount = 750,
            favourite = true,
            type = "popular",
            index = 1
        )

        val movie3 = Movie(
            adult = false,
            backdropPath = "/backdrop3.jpg",
            genreIds = listOf(35, 14),
            id = 3,
            originalLanguage = "en",
            originalTitle = "Movie Three",
            overview = "This is the overview of Movie Three.",
            popularity = 67.89,
            posterPath = "/poster3.jpg",
            releaseDate = "2023-09-10",
            title = "Movie Three",
            video = true,
            voteAverage = 8.2,
            voteCount = 1200,
            favourite = false,
            type = "popular",
            index = 2
        )
        val users = listOf(movie1,movie2,movie3)
        val movieList = MovieList(1,users,3,3)
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(movieList))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getPopularMovies()
        println(actualResponse.body()?.movies?.size)
        //Truth.assertThat(actualResponse.body())
    }
}