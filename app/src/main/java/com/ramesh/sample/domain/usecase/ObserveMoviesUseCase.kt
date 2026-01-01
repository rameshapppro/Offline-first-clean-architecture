package com.ramesh.sample.domain.usecase

import com.ramesh.sample.domain.model.Movie
import com.ramesh.sample.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMoviesUseCase @Inject constructor(
    private val repo: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> = repo.observeMovies()
}
