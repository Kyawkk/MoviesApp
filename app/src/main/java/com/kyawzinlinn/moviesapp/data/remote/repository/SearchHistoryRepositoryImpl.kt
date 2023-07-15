package com.kyawzinlinn.moviesapp.data.remote.repository

import com.kyawzinlinn.moviesapp.data.local.dao.SearchDao
import com.kyawzinlinn.moviesapp.data.local.database.MovieSearchHistory
import com.kyawzinlinn.moviesapp.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchDao: SearchDao
) : SearchHistoryRepository {
    override suspend fun addSearchHistory(movieSearchHistory: MovieSearchHistory) {
        searchDao.addSearchHistory(movieSearchHistory)
    }

    override fun getAllSearches(): Flow<List<MovieSearchHistory>> {
        return searchDao.getAllSearches()
    }

    override suspend fun deleteAllSearches() {
        searchDao.deleteAllSearches()
    }
}