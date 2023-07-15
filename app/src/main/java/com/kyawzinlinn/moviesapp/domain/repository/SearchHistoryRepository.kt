package com.kyawzinlinn.moviesapp.domain.repository

import com.kyawzinlinn.moviesapp.data.local.database.MovieSearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun addSearchHistory(movieSearchHistory: MovieSearchHistory)
    fun getAllSearches(): Flow<List<MovieSearchHistory>>

    suspend fun deleteAllSearches()
}