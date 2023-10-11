package com.lmkhant.moviedb.presentation.screen.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.domain.repository.MovieRepository
import com.lmkhant.moviedb.presentation.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {
    private val _movieDetailsUiState =
        MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val movieDetailsUiState: Flow<MovieUiState> = _movieDetailsUiState

    fun setMovieDetail(id: Int) {
        viewModelScope.launch {
            _movieDetailsUiState.value = MovieUiState.Loading
            val moviesUiStateListDeferred = async {
                movieRepository.getMovie(id)
            }
            val movieDetails = moviesUiStateListDeferred.await()
            withContext(Dispatchers.Main) {
                _movieDetailsUiState.value = MovieUiState.Success(movieDetails)
            }
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.updateMovie(movie)
        }
    }

}