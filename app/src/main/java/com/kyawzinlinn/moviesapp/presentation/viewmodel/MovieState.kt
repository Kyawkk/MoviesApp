package com.kyawzinlinn.moviesapp.presentation.viewmodel

import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto

data class MovieState(
    val isLoading: Boolean = false,
    val data: Any? = null,
    val error: String = ""
)