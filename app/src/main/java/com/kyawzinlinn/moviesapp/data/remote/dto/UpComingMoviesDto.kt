package com.kyawzinlinn.moviesapp.data.remote.dto

data class UpComingMoviesDto(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
