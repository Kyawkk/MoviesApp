package com.kyawzinlinn.moviesapp.presentation.now_playing_movie

import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto

data class NowPlayingMovieState(
    val isLoading: Boolean = false,
    val nowPlayingMovies: NowPlayingMoviesDto? = null,
    val error: String = ""
)
