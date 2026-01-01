package com.ramesh.sample.ui.state

import com.ramesh.sample.domain.model.Movie
import com.ramesh.sample.domain.model.MovieCollection

data class MovieUiState(
    val loading: Boolean = false,
    val movies: List<Movie> = emptyList(),
)
