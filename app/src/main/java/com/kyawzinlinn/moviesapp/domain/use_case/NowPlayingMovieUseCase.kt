package com.kyawzinlinn.moviesapp.domain.use_case

import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NowPlayingMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<NowPlayingMoviesDto>> = flow {
        try {
            emit(Resource.Loading())

            val nowPlayingMovies = repository.getNowPlayingMovies()

            emit(Resource.Success(nowPlayingMovies))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}