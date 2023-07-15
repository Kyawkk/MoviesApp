package com.kyawzinlinn.moviesapp.domain.use_case.movie

import com.kyawzinlinn.moviesapp.data.local.dao.MovieDao
import com.kyawzinlinn.moviesapp.data.remote.dto.SearchMoviesDto
import com.kyawzinlinn.moviesapp.domain.repository.MovieRepository
import com.kyawzinlinn.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchHistoryUseCase @Inject constructor(
    val repository: MovieRepository,
    val movieDao: MovieDao
) {
    operator fun invoke(query: String, page: String): Flow<Resource<SearchMoviesDto>> = flow {
        try {
            emit(Resource.Loading())

            val searchMovies = movieDao.getMovies("")

            //emit(Resource.Success(searchMovies))
        }catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}