package com.kyawzinlinn.moviesapp.domain.repository

import com.kyawzinlinn.moviesapp.data.remote.dto.MovieDetailsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.PopularMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.UpComingMoviesDto

interface MovieRepository {
    suspend fun getNowPlayingMovies(page:String): NowPlayingMoviesDto

    suspend fun getPopularMovies(page: String): PopularMoviesDto

    suspend fun getTopRatedMovies(page: String): TopRatedMoviesDto

    suspend fun getUpComingMovies(page: String): UpComingMoviesDto

    suspend fun getMovieDetails(movieId: String): MovieDetailsDto
}