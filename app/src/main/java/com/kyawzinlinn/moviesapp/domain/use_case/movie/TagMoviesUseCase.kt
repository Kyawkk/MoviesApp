package com.kyawzinlinn.moviesapp.domain.use_case.movie

import com.kyawzinlinn.moviesapp.data.local.dao.MovieDao
import com.kyawzinlinn.moviesapp.data.remote.dto.TagMoviesDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TagMoviesUseCase @Inject constructor(
    val repository: MovieRepository,
    val movieDao: MovieDao
) {
    operator fun invoke(genreId: String, page: String): Flow<Resource<TagMoviesDto>> = flow {
        emit(Resource.Loading())
        try {

            val tagMovies = repository.getMoviesByTagName(genreId,page)
            emit(Resource.Success(tagMovies))

        }catch (e: Exception){
            when(e){
                is IOException -> emit(Resource.Error("Network Unavailable: Please check your internet connection and try again."))
                is HttpException -> emit(Resource.Error("An error occurred. Please check your internet connection."))
                else -> emit(Resource.Error(e.message.toString()))
            }
        }
    }
}