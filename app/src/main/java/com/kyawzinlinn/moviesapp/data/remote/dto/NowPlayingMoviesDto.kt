package com.kyawzinlinn.moviesapp.data.remote.dto

data class NowPlayingMoviesDto(
    val dates: Dates? = null,
    val page: Int? = null,
    val results: List<Movie>,
    val total_pages: Int? = null,
    val total_results: Int? = null
)