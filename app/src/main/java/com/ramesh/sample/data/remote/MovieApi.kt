package com.ramesh.sample.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("./")
    suspend fun getMovies(@Query("s") searchTerm: String): SearchResponseDto
}
