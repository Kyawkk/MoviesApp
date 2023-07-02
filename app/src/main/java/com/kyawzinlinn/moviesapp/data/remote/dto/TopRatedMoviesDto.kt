package com.kyawzinlinn.moviesapp.data.remote.dto

data class TopRatedMoviesDto(
    val page: Int? = 0,
    val results: List<Movie>,
    val total_pages: Int? = 0,
    val total_results: Int? = 0
)
