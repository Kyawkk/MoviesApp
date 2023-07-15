package com.kyawzinlinn.moviesapp.domain.use_case.movie

import com.kyawzinlinn.moviesapp.data.local.dao.MovieDao
import com.kyawzinlinn.moviesapp.data.local.database.toMovieDto
import com.kyawzinlinn.moviesapp.data.remote.dto.UpComingMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.toDatabaseMovie
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.MovieType
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpComingMovieUseCase @Inject constructor(
    val repository: MovieRepository,
    val movieDao: MovieDao
) {
    operator fun invoke(page: String): Flow<Resource<UpComingMoviesDto>> = flow {
        emit(Resource.Loading())

        val type = MovieType.UPCOMING

        val moviesFromDb = movieDao.getMovies(type.toString()).toMovieDto(type) as UpComingMoviesDto
        emit(Resource.Loading(data = moviesFromDb))
        try {

            val moviesFromApi = repository.getUpComingMovies(page)

            movieDao.deleteMovies(type.toString())
            movieDao.insertAll(moviesFromApi.results.toDatabaseMovie(type.toString()))

            android.os.Handler().postDelayed(Runnable {

            },0)

            emit(Resource.Success(moviesFromApi))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }

        val newMovies = movieDao.getMovies(type.toString()).toMovieDto(type) as UpComingMoviesDto

        emit(Resource.Success(newMovies))
    }
}