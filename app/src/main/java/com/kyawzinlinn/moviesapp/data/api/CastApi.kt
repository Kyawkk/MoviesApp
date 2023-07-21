package com.kyawzinlinn.moviesapp.data.api

import com.kyawzinlinn.moviesapp.data.remote.dto.CastDetailsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.CastsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.MoviesOfCastDto
import com.kyawzinlinn.moviesapp.data.remote.dto.CastProfilesDto
import com.kyawzinlinn.moviesapp.utils.TOKEN
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CastApi {
    @GET("movie/{movie_id}/credits")
    suspend fun getCastsAndCrews(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Path("movie_id") movieId: String
    ): CastsDto

    @GET("person/{cast_id}")
    suspend fun getCastDetails(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Path("cast_id") castId: String
    ): CastDetailsDto

    @GET("person/{cast_id}/movie_credits")
    suspend fun getMoviesOfCast(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Path("cast_id") castId: String
    ): MoviesOfCastDto

    @GET("person/{cast_id}/images")
    suspend fun getCastProfiles(
        @Header("Authorization") token: String = "Bearer ".plus(TOKEN),
        @Path("cast_id") castId: String
    ): CastProfilesDto

}