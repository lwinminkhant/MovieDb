package com.lmkhant.moviedb.presentation

import com.lmkhant.moviedb.domain.model.genre.Genre
import com.lmkhant.moviedb.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

sealed interface GenreUiState {
    data class Success(val data: Flow<List<Genre>>) : GenreUiState
    object Loading : GenreUiState
    data class Error(val message: String?): GenreUiState
}