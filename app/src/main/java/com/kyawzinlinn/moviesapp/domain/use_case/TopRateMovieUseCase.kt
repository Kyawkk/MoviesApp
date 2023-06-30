package com.kyawzinlinn.moviesapp.domain.use_case

import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopRateMovieUseCase @Inject constructor(
    val repository: MovieRepository
) {
    operator fun invoke(page: String): Flow<Resource<TopRatedMoviesDto>> = flow {
        try {
            emit(Resource.Loading())

            val topRatedMovies = repository.getTopRatedMovies(page)

            emit(Resource.Success(topRatedMovies))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}