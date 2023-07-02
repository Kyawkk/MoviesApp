package com.kyawzinlinn.moviesapp.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("select * from movie_database where type in(:type)")
    suspend fun getMovies(type: String): List<DatabaseMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<DatabaseMovie>)

    @Query("delete from movie_database where type in(:type)")
    suspend fun deleteMovies(type: String)


}