package com.kyawzinlinn.moviesapp.domain.use_case

import com.kyawzinlinn.moviesapp.data.remote.dto.MovieDetailsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.UpComingMoviesDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    val repository: MovieRepository
) {
    operator fun invoke(movieId: String): Flow<Resource<MovieDetailsDto>> = flow {
        try {
            emit(Resource.Loading())

            val movieDetails = repository.getMovieDetails(movieId)

            emit(Resource.Success(movieDetails))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}