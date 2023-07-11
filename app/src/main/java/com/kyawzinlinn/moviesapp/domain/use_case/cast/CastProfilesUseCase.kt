package com.kyawzinlinn.moviesapp.domain.use_case.cast

import com.kyawzinlinn.moviesapp.data.remote.dto.CastProfilesDto
import com.kyawzinlinn.moviesapp.domain.repository.CastRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CastProfilesUseCase @Inject constructor(
    val repository: CastRepository
) {
    operator fun invoke(castId: String): Flow<Resource<CastProfilesDto>> = flow {
        try {
            emit(Resource.Loading())

            val castProfilesDto = repository.getCastProfiles(castId)
            emit(Resource.Success(castProfilesDto))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}