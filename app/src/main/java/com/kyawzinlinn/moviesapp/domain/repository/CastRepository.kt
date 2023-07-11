package com.kyawzinlinn.moviesapp.domain.repository

import com.kyawzinlinn.moviesapp.data.remote.dto.CastDetailsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.CastsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.MoviesOfCastDto
import com.kyawzinlinn.moviesapp.data.remote.dto.CastProfilesDto

interface CastRepository {
    suspend fun getCastsAndCrews(movieId: String): CastsDto
    suspend fun getCastDetails(castId: String): CastDetailsDto
    suspend fun getMoviesOfCast(castId: String): MoviesOfCastDto
    suspend fun getCastProfiles(castId: String): CastProfilesDto
}