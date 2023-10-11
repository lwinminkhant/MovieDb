package com.lmkhant.moviedb.presentation

import com.lmkhant.moviedb.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

sealed interface MovieUiState {
    data class Success(val data: Flow<Movie>) : MovieUiState
    object Loading : MovieUiState
    data class Error(val message: String?): MovieUiState
}