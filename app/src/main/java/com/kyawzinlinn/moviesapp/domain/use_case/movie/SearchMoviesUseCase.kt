package com.kyawzinlinn.moviesapp.domain.use_case.movie

import android.util.Log
import com.kyawzinlinn.moviesapp.data.local.dao.MovieDao
import com.kyawzinlinn.moviesapp.data.local.database.toMovieDto
import com.kyawzinlinn.moviesapp.data.remote.dto.SearchMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.toDatabaseMovie
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.MovieType
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.log

class SearchMoviesUseCase @Inject constructor(
    val repository: MovieRepository,
    val movieDao: MovieDao
) {
    operator fun invoke(query: String, page: String): Flow<Resource<SearchMoviesDto>> = flow {
        emit(Resource.Loading())
        val type = MovieType.SEARCH_RESULTS
        try {
            val searchMovies = repository.getSearchMovies(query,page)

            if (searchMovies.results.size != 0){
                val searchDatabaseMovies = searchMovies.results.toDatabaseMovie(type.toString())

                movieDao.deleteMovies(type.toString())
                movieDao.insertAll(searchDatabaseMovies)
                emit(Resource.Success(searchMovies))
            }

        }catch (e: Exception){
            val cachedSearchResults = movieDao.searchMovies(query).toMovieDto(type) as SearchMoviesDto

            emit(Resource.Success(cachedSearchResults))

            emit(Resource.Error(e.message.toString()))
        }


    }
}