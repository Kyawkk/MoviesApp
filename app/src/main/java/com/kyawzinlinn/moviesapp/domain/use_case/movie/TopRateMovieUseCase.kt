package com.kyawzinlinn.moviesapp.domain.use_case.movie

import com.kyawzinlinn.moviesapp.data.local.dao.MovieDao
import com.kyawzinlinn.moviesapp.data.local.database.toMovieDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.toDatabaseMovie
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.MovieType
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TopRateMovieUseCase @Inject constructor(
    val repository: MovieRepository,
    val movieDao: MovieDao
) {
    operator fun invoke(page: String): Flow<Resource<TopRatedMoviesDto>> = flow {
        emit(Resource.Loading())

        val type = MovieType.TOP_RATED
        try {

            val moviesFromApi = withContext(Dispatchers.IO){repository.getTopRatedMovies(page)}
            withContext(Dispatchers.IO){
                movieDao.deleteMovies(type.toString())
                movieDao.insertAll(moviesFromApi.results.toDatabaseMovie(type.toString()).take(10))
            }
        }catch (e: Exception){
            when(e){
                is IOException -> emit(Resource.Error("Network Unavailable: Please check your internet connection and try again."))
                is HttpException -> emit(Resource.Error("An error occurred. Please check your internet connection."))
                else -> emit(Resource.Error(e.message.toString()))
            }
        }

        val newMovies = movieDao.getMovies(type.toString()).toMovieDto(type) as TopRatedMoviesDto
        emit(Resource.Success(newMovies))
    }
}