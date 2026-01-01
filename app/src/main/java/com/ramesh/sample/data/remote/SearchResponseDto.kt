package com.ramesh.sample.data.remote

import com.google.gson.annotations.SerializedName
import com.ramesh.sample.data.local.MovieEntity

data class SearchResponseDto(
    @SerializedName("Search") val search: List<Search> = emptyList(),
    @SerializedName("totalResults") val totalResults: String? = null,
    @SerializedName("Response") val response: String? = null
)

data class Search(
    @SerializedName("Title") val title: String?,
    @SerializedName("Year") val year: String?,
    @SerializedName("imdbID") val imdbID: String?,
    @SerializedName("Type") val type: String?,
    @SerializedName("Poster") val poster: String?
)

fun Search.toEntity() = MovieEntity(
    imdbID = imdbID ?: "",
    title = title,
    year = year,
    poster = poster
)
