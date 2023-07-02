package com.kyawzinlinn.moviesapp.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kyawzinlinn.moviesapp.App
import com.kyawzinlinn.moviesapp.data.local.database.DatabaseMovie
import com.kyawzinlinn.moviesapp.data.local.database.MovieDao
import com.kyawzinlinn.moviesapp.data.local.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext application: Context): MovieDatabase {
        return Room.databaseBuilder(
            application,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

    /*@Provides
    @Singleton
    fun provideMovieDatabaseRepository(dao: MovieDao): MovieDatabaseRepository{
        return MovieDatabaseRepository(dao)
    }*/

}