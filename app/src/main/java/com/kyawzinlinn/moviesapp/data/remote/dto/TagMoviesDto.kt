package com.kyawzinlinn.moviesapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TagMoviesDto(
    val page: Int? = 0,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int? = 0,
    @SerializedName("total_results") val totalResults: Int? = 0,
)