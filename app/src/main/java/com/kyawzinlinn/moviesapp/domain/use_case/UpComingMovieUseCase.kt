package com.kyawzinlinn.moviesapp.domain.use_case

import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.UpComingMoviesDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpComingMovieUseCase @Inject constructor(
    val repository: MovieRepository
) {
    operator fun invoke(page: String): Flow<Resource<UpComingMoviesDto>> = flow {
        try {
            emit(Resource.Loading())

            val upComingMoviesDto = repository.getUpComingMovies(page)

            emit(Resource.Success(upComingMoviesDto))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}