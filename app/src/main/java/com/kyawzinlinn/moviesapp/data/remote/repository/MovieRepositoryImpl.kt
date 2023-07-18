package com.kyawzinlinn.moviesapp.data.remote.repository

import com.kyawzinlinn.moviesapp.data.remote.MovieApi
import com.kyawzinlinn.moviesapp.data.remote.dto.MovieDetailsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TagMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.PopularMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.SearchMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.SimilarMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TrailersDto
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

    override suspend fun getMovieDetails(movieId: String): MovieDetailsDto {
        return api.getMovieDetails(movieId = movieId)
    }

    override suspend fun getSimilarMovies(movieId: String, page: String): SimilarMoviesDto{
        return api.getSimilarMovies(movieId = movieId, page = page)
    }

    override suspend fun getMoviesByTagName(genreId: String, page: String): TagMoviesDto {
        return api.getMoviesByTagName(genreId = genreId, page = page)
    }

    override suspend fun getSearchMovies(query: String, page: String): SearchMoviesDto {
        return api.searchMovies(query = query, page = page, isAdult = true)
    }

    override suspend fun getMovieTrailers(movieId: String): TrailersDto {
        return api.getMovieTrailers(movieId = movieId)
    }
}