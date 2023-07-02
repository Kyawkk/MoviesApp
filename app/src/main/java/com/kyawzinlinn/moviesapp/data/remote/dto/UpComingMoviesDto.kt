package com.kyawzinlinn.moviesapp.data.remote.dto

data class UpComingMoviesDto(
    val dates: Dates? = null,
    val page: Int? = 0,
    val results: List<Movie>,
    val total_pages: Int? = 0,
    val total_results: Int? = 0
)
