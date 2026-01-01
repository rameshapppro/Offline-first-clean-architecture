package com.ramesh.sample.domain.model

import com.ramesh.sample.data.local.MovieEntity

data class Movie(
    val imdbID: String,
    val title: String?,
    val year: String?,
    val poster: String?
)

fun MovieEntity.toDomain(): Movie = Movie(
    imdbID = imdbID,
    title = title,
    year = year,
    poster = poster
)
