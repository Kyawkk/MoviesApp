package com.kyawzinlinn.moviesapp.data.remote.repository

import com.kyawzinlinn.moviesapp.data.remote.MovieApi
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.PopularMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.UpComingMoviesDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
): MovieRepository {
    override suspend fun getNowPlayingMovies(page: String): NowPlayingMoviesDto {
        return api.getNowPlayingMovies(page = page)
    }

    override suspend fun getPopularMovies(page: String): PopularMoviesDto {
        return api.getPopularMovies(page = page)
    }

    override suspend fun getTopRatedMovies(page: String): TopRatedMoviesDto {
        return api.getTopRatedMovies(page = page)
    }

    override suspend fun getUpComingMovies(page: String): UpComingMoviesDto {
        return api.getUpComingMovies(page = page)
    }
}