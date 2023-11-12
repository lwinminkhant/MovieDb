package com.lmkhant.moviedb.presentation

import com.lmkhant.moviedb.domain.model.credits.Credit
import com.lmkhant.moviedb.domain.model.genre.Genre
import com.lmkhant.moviedb.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

sealed interface MovieCreditsUiState {
    data class Success(val data: Credit) : MovieCreditsUiState
    object Loading : MovieCreditsUiState
    data class Error(val message: String?): MovieCreditsUiState
}