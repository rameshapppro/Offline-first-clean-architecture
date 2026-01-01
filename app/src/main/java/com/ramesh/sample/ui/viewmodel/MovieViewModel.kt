package com.ramesh.sample.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramesh.sample.domain.model.MovieCollection
import com.ramesh.sample.domain.repository.MovieRepository
import com.ramesh.sample.domain.usecase.ObserveMoviesUseCase
import com.ramesh.sample.ui.state.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val observeMovies: ObserveMoviesUseCase,
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MovieUiState())
    val state: StateFlow<MovieUiState> = _state

    fun onSearch(searchTerm: String) {
        viewModelScope.launch {
            try {
                repository.searchMovies(searchTerm)
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error searching movies", e)
            }
        }
    }

    init {
        onSearch("movie")
        observe()
    }

    private fun observe() {
        viewModelScope.launch {
            observeMovies()
                .onStart { _state.value = _state.value.copy(loading = true) }
                .collect { movies ->
                    Log.d("MovieViewModel", "observe: "+movies.toString())
                    _state.value = _state.value.copy(movies = movies)
                }
        }
    }


}
