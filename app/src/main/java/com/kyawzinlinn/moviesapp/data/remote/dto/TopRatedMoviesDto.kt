package com.kyawzinlinn.moviesapp.data.remote.dto

data class TopRatedMoviesDto(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
