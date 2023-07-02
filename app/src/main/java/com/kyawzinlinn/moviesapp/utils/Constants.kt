package com.kyawzinlinn.moviesapp.utils

const val BASE_URL = "https://api.themoviedb.org/3/"
const val IMG_URL_PREFIX = "https://image.tmdb.org/t/p/w300"
const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNzhmZjBiMTExNWY1NzJhMDFjNDJlZWZkNTIzNGU2NyIsInN1YiI6IjYwMzRhMjg5MTEzMGJkMDAzZTQ0NWU5YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ZT1mdPXbGsIWhcJsZLUqyg_ksC5povwtbMVwWIVNfKY"

const val MOVIE_ID_INTENT_EXTRA = "movie_id_intent_extra"

const val TAG = "Log TAG"

enum class MovieType{
    NOW_PLAYING, POPULAR, TOP_RATED, UPCOMING
}