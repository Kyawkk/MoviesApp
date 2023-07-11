package com.kyawzinlinn.moviesapp.domain.use_case.cast

import com.kyawzinlinn.moviesapp.data.remote.dto.Movie
import com.kyawzinlinn.moviesapp.data.remote.dto.MoviesOfCastDto
import com.kyawzinlinn.moviesapp.domain.repository.CastRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesOfCastUseCase @Inject constructor(
    val repository: CastRepository
) {
    operator fun invoke(castId: String): Flow<Resource<MoviesOfCastDto>> = flow {
        try {
            emit(Resource.Loading())

            val moviesOfCast = repository.getMoviesOfCast(castId)
            emit(Resource.Success(moviesOfCast))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}