package com.kyawzinlinn.moviesapp.domain.use_case

import com.kyawzinlinn.moviesapp.data.local.database.MovieDao
import com.kyawzinlinn.moviesapp.data.local.database.toMovieDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.toDatabaseMovie
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.MovieType
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopRateMovieUseCase @Inject constructor(
    val repository: MovieRepository,
    val movieDao: MovieDao
) {
    operator fun invoke(page: String): Flow<Resource<TopRatedMoviesDto>> = flow {
        emit(Resource.Loading())

        val type = MovieType.TOP_RATED

        val moviesFromDb = movieDao.getMovies(type.toString()).toMovieDto(type) as TopRatedMoviesDto
        emit(Resource.Loading(data = moviesFromDb))
        try {

            val moviesFromApi = repository.getTopRatedMovies(page)

            movieDao.deleteMovies(type.toString())
            movieDao.insertAll(moviesFromApi.results.toDatabaseMovie(type.toString()))

            emit(Resource.Success(moviesFromApi))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }

        val newMovies = movieDao.getMovies(type.toString()).toMovieDto(type) as TopRatedMoviesDto
        emit(Resource.Success(newMovies))
    }
}