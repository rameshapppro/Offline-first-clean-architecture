package com.ramesh.sample.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("movie/popular")
    suspend fun getMovies(): List<SearchResponseDto>

    @GET("/")
    suspend fun getMovies(
        @Query("s") search: String,
        @Query("page") page: Int = 1
    ): SearchResponseDto
}
