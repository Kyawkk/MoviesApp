package com.kyawzinlinn.moviesapp.data.remote

import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.utils.TOKEN
import retrofit2.http.GET
import retrofit2.http.Header

interface MovieApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN)
    ): NowPlayingMoviesDto
}