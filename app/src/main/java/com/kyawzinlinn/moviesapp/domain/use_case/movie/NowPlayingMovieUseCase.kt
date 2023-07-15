package com.kyawzinlinn.moviesapp.domain.use_case.movie

import android.util.Log
import com.kyawzinlinn.moviesapp.data.local.dao.MovieDao
import com.kyawzinlinn.moviesapp.data.local.database.toMovieDto
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.toDatabaseMovie
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.MovieType
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NowPlayingMovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val movieDao: MovieDao
) {
    operator fun invoke(page: String): Flow<Resource<NowPlayingMoviesDto>> = flow {
        emit(Resource.Loading())

        val type = MovieType.NOW_PLAYING

        val moviesFromDb = movieDao.getMovies(type.toString()).toMovieDto(type) as NowPlayingMoviesDto
        //emit(Resource.Loading(data = moviesFromDb))

        try {

            val moviesFromApi = repository.getNowPlayingMovies(page)

            movieDao.deleteMovies(type.toString())
            val databaseMovies = moviesFromApi.results.toDatabaseMovie(type.toString())
            movieDao.insertAll(databaseMovies)

            //emit(Resource.Success(moviesFromApi))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }

        val newMovies = movieDao.getMovies(type.toString()).toMovieDto(type) as NowPlayingMoviesDto

        Log.d("TAG", "after deleting: new ${movieDao.getMovies(type.toString())}")
        emit(Resource.Success(newMovies))
    }
}