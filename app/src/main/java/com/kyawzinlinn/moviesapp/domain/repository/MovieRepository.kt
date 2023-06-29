package com.kyawzinlinn.moviesapp.domain.repository

import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto

interface MovieRepository {
    suspend fun getNowPlayingMovies(): NowPlayingMoviesDto
}