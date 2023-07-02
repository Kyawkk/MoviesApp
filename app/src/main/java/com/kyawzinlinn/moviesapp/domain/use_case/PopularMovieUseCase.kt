package com.kyawzinlinn.moviesapp.domain.use_case

import com.kyawzinlinn.moviesapp.data.local.database.MovieDao
import com.kyawzinlinn.moviesapp.data.local.database.toMovieDto
import com.kyawzinlinn.moviesapp.data.remote.dto.PopularMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.toDatabaseMovie
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.MovieType
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PopularMovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val movieDao: MovieDao
) {
    operator fun invoke(page: String): Flow<Resource<PopularMoviesDto>> = flow {
        emit(Resource.Loading())

        val type = MovieType.POPULAR

        val moviesFromDb = movieDao.getMovies(type.toString()).toMovieDto(type) as PopularMoviesDto
        emit(Resource.Loading(data = moviesFromDb))
        try {

            val moviesFromApi = repository.getPopularMovies(page)

            movieDao.deleteMovies(type.toString())
            movieDao.insertAll(moviesFromApi.results.toDatabaseMovie(type.toString()))

            emit(Resource.Success(moviesFromApi))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }

        val newMovies = movieDao.getMovies(type.toString()).toMovieDto(type) as PopularMoviesDto
        emit(Resource.Success(newMovies))
    }
}