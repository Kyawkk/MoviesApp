package com.kyawzinlinn.moviesapp.data.remote

import com.kyawzinlinn.moviesapp.data.remote.dto.MovieDetailsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TagMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.PopularMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.SearchMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.SimilarMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TrailersDto
import com.kyawzinlinn.moviesapp.data.remote.dto.UpComingMoviesDto
import com.kyawzinlinn.moviesapp.utils.TOKEN
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Query("page")page: String
    ): NowPlayingMoviesDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Query("page")page: String
    ): PopularMoviesDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Query("page")page: String
    ): TopRatedMoviesDto

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Query("page")page: String
    ): UpComingMoviesDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Path("movie_id") movieId: String
    ): MovieDetailsDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Path("movie_id") movieId: String,
        @Query("page") page: String,
    ): SimilarMoviesDto

    @GET("discover/movie")
    suspend fun getMoviesByTagName(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Query("with_genres") genreId: String,
        @Query("page") page: String,
    ): TagMoviesDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Query("query") query: String,
        @Query("is_adult") isAdult: Boolean,
        @Query("page") page: String,
    ): SearchMoviesDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Path("movie_id") movieId: String
    ): TrailersDto


}