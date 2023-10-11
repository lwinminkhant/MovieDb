package com.lmkhant.moviedb.presentation

import com.lmkhant.moviedb.domain.model.movie.Movie
import kotlinx.coroutines.flow.StateFlow

sealed interface MovieGridUiState {
    data class Success(val data: StateFlow<List<Movie>>) : MovieGridUiState
    object Loading : MovieGridUiState
    data class Error(val message: String?): MovieGridUiState
}