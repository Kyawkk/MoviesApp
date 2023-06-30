package com.kyawzinlinn.moviesapp.domain.use_case

import com.kyawzinlinn.moviesapp.data.remote.dto.PopularMoviesDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PopularMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(page: String): Flow<Resource<PopularMoviesDto>> = flow {
        try {
            emit(Resource.Loading())

            val popularMovies = repository.getPopularMovies(page)

            emit(Resource.Success(popularMovies))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}