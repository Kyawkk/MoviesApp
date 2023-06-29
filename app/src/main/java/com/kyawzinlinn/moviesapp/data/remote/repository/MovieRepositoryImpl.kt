package com.kyawzinlinn.moviesapp.data.remote.repository

import com.kyawzinlinn.moviesapp.data.remote.MovieApi
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
): MovieRepository {
    override suspend fun getNowPlayingMovies(): NowPlayingMoviesDto {
        return api.getNowPlayingMovies()
    }
}