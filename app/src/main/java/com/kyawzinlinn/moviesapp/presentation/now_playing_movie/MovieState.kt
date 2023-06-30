package com.kyawzinlinn.moviesapp.presentation.now_playing_movie

import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto

data class MovieState(
    val isLoading: Boolean = false,
    val data: Any? = null,
    val error: String = ""
)