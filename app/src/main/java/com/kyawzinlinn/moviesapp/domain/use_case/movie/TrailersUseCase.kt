package com.kyawzinlinn.moviesapp.domain.use_case.movie

import android.util.Log
import com.kyawzinlinn.moviesapp.data.local.dao.MovieDao
import com.kyawzinlinn.moviesapp.data.remote.dto.SearchMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TrailersDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrailersUseCase @Inject constructor(
    val repository: MovieRepository,
    val movieDao: MovieDao
) {
    operator fun invoke(movieId: String): Flow<Resource<TrailersDto>> = flow {
        emit(Resource.Loading())

        try {
            val trailersMovies = repository.getMovieTrailers(movieId)
            emit(Resource.Success(trailersMovies))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}