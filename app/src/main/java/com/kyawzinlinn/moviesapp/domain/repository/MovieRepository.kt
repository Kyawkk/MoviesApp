package com.kyawzinlinn.moviesapp.domain.repository

import com.kyawzinlinn.moviesapp.data.remote.dto.MovieDetailsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TagMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.PopularMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.SearchMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.SimilarMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.UpComingMoviesDto

interface MovieRepository {
    suspend fun getNowPlayingMovies(page:String): NowPlayingMoviesDto

    suspend fun getPopularMovies(page: String): PopularMoviesDto

    suspend fun getTopRatedMovies(page: String): TopRatedMoviesDto

    suspend fun getUpComingMovies(page: String): UpComingMoviesDto

    suspend fun getMovieDetails(movieId: String): MovieDetailsDto

    suspend fun getSimilarMovies(movieId: String, page: String): SimilarMoviesDto
    suspend fun getMoviesByTagName(genreId: String, page: String): TagMoviesDto

    suspend fun getSearchMovies(query: String, page: String): SearchMoviesDto
}