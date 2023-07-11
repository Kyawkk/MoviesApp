package com.kyawzinlinn.moviesapp.domain.use_case.cast

import com.kyawzinlinn.moviesapp.data.remote.dto.CastsDto
import com.kyawzinlinn.moviesapp.domain.repository.CastRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieCastsUseCase @Inject constructor(
    val repository: CastRepository
) {
    operator fun invoke(movieId: String): Flow<Resource<CastsDto>> = flow {
        try {
            emit(Resource.Loading())

            val movieCasts = repository.getCastsAndCrews(movieId)
            emit(Resource.Success(movieCasts))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}