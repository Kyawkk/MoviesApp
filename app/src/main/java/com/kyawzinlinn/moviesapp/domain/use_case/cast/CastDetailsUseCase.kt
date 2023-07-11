package com.kyawzinlinn.moviesapp.domain.use_case.cast

import com.kyawzinlinn.moviesapp.data.remote.dto.CastDetailsDto
import com.kyawzinlinn.moviesapp.domain.repository.CastRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CastDetailsUseCase @Inject constructor(
    val repository: CastRepository
) {
    operator fun invoke(castId: String): Flow<Resource<CastDetailsDto>> = flow {
        try {
            emit(Resource.Loading())

            val castDetails = repository.getCastDetails(castId)
            emit(Resource.Success(castDetails))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}