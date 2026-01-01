package com.ramesh.sample.domain.repository

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun observeMovies(): Flow<List<com.ramesh.sample.domain.model.Movie>>
    suspend fun searchMovies(searchTerm: String)
}
