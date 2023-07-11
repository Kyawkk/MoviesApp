package com.kyawzinlinn.moviesapp.domain.use_case

import com.kyawzinlinn.moviesapp.data.local.database.MovieDao
import com.kyawzinlinn.moviesapp.data.remote.dto.SimilarMoviesDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SimilarMoviesUseCase @Inject constructor(
    val repository: MovieRepository,
    val movieDao: MovieDao
) {
    operator fun invoke(movieId: String, page: String): Flow<Resource<SimilarMoviesDto>> = flow {
        try {
            emit(Resource.Loading())

            val similarMovies = repository.getSimilarMovies(movieId,page)

            emit(Resource.Success(similarMovies))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}