package com.kyawzinlinn.moviesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyawzinlinn.moviesapp.data.local.database.DatabaseMovie
import com.kyawzinlinn.moviesapp.data.local.database.MovieSearchHistory

@Dao
interface MovieDao {
    @Query("select * from movie_database where type in(:type)")
    suspend fun getMovies(type: String): List<DatabaseMovie>

    @Query("select * from movie_database where title like '%' || :query || '%' ")
    suspend fun searchMovies(query: String): List<DatabaseMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<DatabaseMovie>)

    @Query("delete from movie_database where type = :type")
    suspend fun deleteMovies(type: String)

}