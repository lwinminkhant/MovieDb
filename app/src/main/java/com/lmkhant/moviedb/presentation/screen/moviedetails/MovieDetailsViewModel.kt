package com.lmkhant.moviedb.presentation.screen.moviedetails

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmkhant.moviedb.data.network.NoConnectivityException
import com.lmkhant.moviedb.domain.model.genre.Genre
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.domain.repository.MovieRepository
import com.lmkhant.moviedb.presentation.GenreUiState
import com.lmkhant.moviedb.presentation.MovieCreditsUiState
import com.lmkhant.moviedb.presentation.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    private val _movieDetailsUiState =
        MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val movieDetailsUiState: Flow<MovieUiState> = _movieDetailsUiState


    private val _genres = MutableStateFlow<GenreUiState>(GenreUiState.Loading)
    val genres = _genres

    private val _movieCreditsUiState =
        MutableStateFlow<MovieCreditsUiState>(MovieCreditsUiState.Loading)
    val movieCreditsUiState: Flow<MovieCreditsUiState> = _movieCreditsUiState

    init {
        viewModelScope.launch {
            try {
                _genres.value = GenreUiState.Success(movieRepository.getGenreList())
            } catch (e: NoConnectivityException) {
                Timber.e(e.message)
            }
        }

    }

    fun setMovieDetail(id: Int) {
        viewModelScope.launch {
            try {
                _movieDetailsUiState.value = MovieUiState.Loading
                val moviesUiStateListDeferred = async {
                    movieRepository.getMovie(id)
                }
                val movieDetails = moviesUiStateListDeferred.await()
                withContext(Dispatchers.Main) {
                    _movieDetailsUiState.value = MovieUiState.Success(movieDetails)


                    _movieCreditsUiState.value =
                        MovieCreditsUiState.Success(movieRepository.getMovieCredits(id))
                }
            } catch (e: NoConnectivityException) {
                Timber.e(e.message)
            }


        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.updateMovie(movie)
        }
    }

}