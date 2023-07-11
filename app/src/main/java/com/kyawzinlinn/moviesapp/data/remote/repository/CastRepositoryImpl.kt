package com.kyawzinlinn.moviesapp.data.remote.repository

import com.kyawzinlinn.moviesapp.data.remote.CastApi
import com.kyawzinlinn.moviesapp.data.remote.dto.CastDetailsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.CastsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.MoviesOfCastDto
import com.kyawzinlinn.moviesapp.domain.repository.CastRepository
import com.kyawzinlinn.moviesapp.data.remote.dto.CastProfilesDto
import javax.inject.Inject

class CastRepositoryImpl @Inject constructor(
    private val api: CastApi
): CastRepository {
    override suspend fun getCastsAndCrews(movieId: String): CastsDto {
        return api.getCastsAndCrews(movieId = movieId)
    }

    override suspend fun getCastDetails(castId: String): CastDetailsDto {
        return api.getCastDetails(castId = castId)
    }

    override suspend fun getMoviesOfCast(castId: String): MoviesOfCastDto = api.getMoviesOfCast(castId = castId)
    override suspend fun getCastProfiles(castId: String): CastProfilesDto = api.getCastProfiles(castId = castId)

}