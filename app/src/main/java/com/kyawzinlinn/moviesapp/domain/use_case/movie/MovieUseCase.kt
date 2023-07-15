package com.kyawzinlinn.moviesapp.domain.use_case.movie

import javax.inject.Inject

data class MovieUseCase @Inject constructor(
    val nowPlayingMovieUseCase: NowPlayingMovieUseCase,
    val popularMovieUseCase: PopularMovieUseCase,
    val topRateMovieUseCase: TopRateMovieUseCase,
    val upComingMovieUseCase: UpComingMovieUseCase,
    val movieDetailsUseCase: MovieDetailsUseCase,
    val similarMoviesUseCase: SimilarMoviesUseCase,
    val tagMoviesUseCase: TagMoviesUseCase,
    val searchMoviesUseCase: SearchMoviesUseCase
)
