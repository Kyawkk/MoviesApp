package com.kyawzinlinn.moviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyawzinlinn.moviesapp.data.local.database.MovieSearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchHistory(movieSearchHistory: MovieSearchHistory)

    @Query("select * from search_history")
    fun getAllSearches(): Flow<List<MovieSearchHistory>>

    @Query("delete from search_history")
    suspend fun deleteAllSearches()
}