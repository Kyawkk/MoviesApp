package com.kyawzinlinn.moviesapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kyawzinlinn.moviesapp.data.local.dao.MovieDao
import com.kyawzinlinn.moviesapp.data.local.dao.SearchDao

@Database(entities = [DatabaseMovie::class, MovieSearchHistory::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun searchDao(): SearchDao
}