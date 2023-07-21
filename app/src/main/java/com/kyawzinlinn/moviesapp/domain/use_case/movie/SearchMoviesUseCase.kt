package com.kyawzinlinn.moviesapp.domain.use_case.movie

import android.util.Log
import com.kyawzinlinn.moviesapp.data.local.dao.MovieDao
import com.kyawzinlinn.moviesapp.data.local.database.toMovieDto
import com.kyawzinlinn.moviesapp.data.remote.dto.SearchMoviesDto
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

class SearchMoviesUseCase @Inject constructor(
    val repository: MovieRepository,
    val movieDao: MovieDao
) {
    operator fun invoke(query: String, page: String): Flow<Resource<SearchMoviesDto>> = flow {
        emit(Resource.Loading())
        val type = MovieType.SEARCH_RESULTS

        try {
            val searchMovies = withContext(Dispatchers.IO){
                repository.getSearchMovies(query, page)
            }

            withContext(Dispatchers.IO){
                val searchDatabaseMovies = searchMovies.results.toDatabaseMovie(type.toString())

                movieDao.deleteMovies(type.toString())

                // store only up to 10 searches not to grow app size
                movieDao.insertAll(if (searchDatabaseMovies.size > 10) searchDatabaseMovies.take(10) else searchDatabaseMovies)
            }

        } catch (e: Exception) {
            when(e){
                is IOException -> emit(Resource.Error("Network Unavailable: Please check your internet connection and try again."))
                is HttpException -> emit(Resource.Error("An error occurred. Please check your internet connection."))
                else -> emit(Resource.Error(e.message.toString()))
            }
        }

        val cachedSearchResults = movieDao.searchMovies(query).toMovieDto(type) as SearchMoviesDto
        emit(Resource.Success(cachedSearchResults))
    }
}