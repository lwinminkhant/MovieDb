package com.lmkhant.moviedb.presentation.screen.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmkhant.moviedb.domain.model.movie.Movie
import com.lmkhant.moviedb.domain.repository.MovieRepository
import com.lmkhant.moviedb.presentation.MovieGridUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _popularMoviesUiState =
        MutableStateFlow<MovieGridUiState> (MovieGridUiState.Loading)
    val popularMoviesUiState: StateFlow<MovieGridUiState> = _popularMoviesUiState

    private val _upComingMoviesUiState =
        MutableStateFlow<MovieGridUiState> (MovieGridUiState.Loading)
    val upComingMoviesUiState: StateFlow<MovieGridUiState> = _upComingMoviesUiState

    private val _favoriteMoviesUiState =
        MutableStateFlow<MovieGridUiState> (MovieGridUiState.Loading)
    val favoriteMoviesUiState: StateFlow<MovieGridUiState> = _favoriteMoviesUiState

    private val _searchMoviesUiState =
        MutableStateFlow<MovieGridUiState> (MovieGridUiState.Loading)

    val searchedMoviesUiState: StateFlow<MovieGridUiState> = _searchMoviesUiState
    init {
        setupPopularMoviesUiStates()
        setupUpComingMoviesUiStates()
        setupFavoriteMoviesUiState()
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.updateMovie(movie)
        }
    }
    fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.save(movie)
        }
    }

    fun fetchNewMovies(){
        viewModelScope.launch {
            movieRepository.fetchNewMovies()
        }
    }

    private fun setupFavoriteMoviesUiState(){
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _favoriteMoviesUiState.value = MovieGridUiState.Error(throwable.message)
        }
        viewModelScope.launch (Dispatchers.IO + coroutineExceptionHandler){
            _favoriteMoviesUiState.value = MovieGridUiState.Loading
            val moviesUiStateListDeferred = async {
                movieRepository.getAllFavoriteMovies()
            }
            val moviesUiStateList = moviesUiStateListDeferred.await()

            withContext(Dispatchers.Main) {
                _favoriteMoviesUiState.value = MovieGridUiState.Success(moviesUiStateList.stateIn(viewModelScope))
            }
        }
    }
    private fun setupPopularMoviesUiStates() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _popularMoviesUiState.value = MovieGridUiState.Error(throwable.message)
        }

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _popularMoviesUiState.value = MovieGridUiState.Loading
            val moviesUiStateListDeferred = async {
                movieRepository.getPopularMovies()
            }
            val moviesUiStateList = moviesUiStateListDeferred.await()

            withContext(Dispatchers.Main) {
                _popularMoviesUiState.value = MovieGridUiState.Success(moviesUiStateList.stateIn(viewModelScope))
            }

        }
    }
    private fun setupUpComingMoviesUiStates() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _upComingMoviesUiState.value = MovieGridUiState.Error(throwable.message)
        }

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _upComingMoviesUiState.value = MovieGridUiState.Loading
            val moviesUiStateListDeferred = async {
                movieRepository.getUpComingMovies()
            }
            val moviesUiStateList = moviesUiStateListDeferred.await()

            withContext(Dispatchers.Main) {
                _upComingMoviesUiState.value = MovieGridUiState.Success(moviesUiStateList.stateIn(viewModelScope))
            }
        }
    }
    fun searchMovies(searchTerm: String) {

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _searchMoviesUiState.value = MovieGridUiState.Error(throwable.message)
        }
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _searchMoviesUiState.value = MovieGridUiState.Loading
            val searchedMoviesUiStateDeferred =
                async { movieRepository.searchByTerm(searchTerm) }
            val searchedMoviesUiState = searchedMoviesUiStateDeferred.await()
            withContext(Dispatchers.Main) {
                _searchMoviesUiState.value = MovieGridUiState.Success(searchedMoviesUiState.stateIn(viewModelScope))
            }
        }
    }
}