package com.ramesh.sample.data.repository

import android.util.Log
import com.ramesh.sample.data.local.MovieDao
import com.ramesh.sample.data.local.MovieEntity
import com.ramesh.sample.data.remote.MovieApi
import com.ramesh.sample.data.remote.toEntity
import com.ramesh.sample.domain.model.Movie
import com.ramesh.sample.domain.model.toDomain
import com.ramesh.sample.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val dao: MovieDao
) : MovieRepository {

    override fun observeMovies(): Flow<List<Movie>> =
        dao.observeMovies().map { it.map(MovieEntity::toDomain) }

    override suspend fun searchMovies(searchTerm: String) {
        Log.d("MovieViewModel", "searchMovies: ")
        val remote = api.getMovies(searchTerm)
        dao.insertAll(remote.search.map { it.toEntity() })
    }
}
