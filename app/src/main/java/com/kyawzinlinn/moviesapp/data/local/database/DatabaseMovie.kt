package com.kyawzinlinn.moviesapp.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kyawzinlinn.moviesapp.data.remote.dto.Movie
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.PopularMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.UpComingMoviesDto
import com.kyawzinlinn.moviesapp.utils.MovieType

@Entity(tableName = "movie_database")
data class DatabaseMovie(
    @PrimaryKey
    val id: Int,
    @ColumnInfo("adult") val adult: Boolean,
    @ColumnInfo("backdrop_path") val backdrop_path: String,
    //@ColumnInfo("genre_ids") val genre_ids: List<Int>,
    @ColumnInfo("original_language") val original_language: String,
    @ColumnInfo("original_title") val original_title: String,
    @ColumnInfo("overview") val overview: String,
    @ColumnInfo("popularity") val popularity: Double,
    @ColumnInfo("poster_path") val poster_path: String,
    @ColumnInfo("release_date") val release_date: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("video") val video: Boolean,
    @ColumnInfo("vote_average") val vote_average: Double,
    @ColumnInfo("vote_count") val vote_count: Int,
    @ColumnInfo("type") val type: String
)

fun List<DatabaseMovie>.toMovie(): List<Movie>{
    return map {
        Movie(
            it.adult,
            it.backdrop_path,
            listOf(),
            it.id,
            it.original_language,
            it.original_title,
            it.overview,
            it.popularity,
            it.poster_path,
            it.release_date,
            it.title,
            it.video,
            it.vote_average,
            it.vote_count
        )
    }
}

fun List<DatabaseMovie>.toMovieDto(dtoType: MovieType): Any{
    return when(dtoType){
        MovieType.NOW_PLAYING -> NowPlayingMoviesDto(results = this.toMovie())
        MovieType.POPULAR -> PopularMoviesDto(results = this.toMovie())
        MovieType.TOP_RATED -> TopRatedMoviesDto(results =  this.toMovie())
        MovieType.UPCOMING -> UpComingMoviesDto(results =  this.toMovie())
    }
}