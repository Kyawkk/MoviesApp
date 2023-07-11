package com.kyawzinlinn.moviesapp.domain.use_case.cast

import javax.inject.Inject

data class CastUseCase @Inject constructor(
    val castsUseCase: MovieCastsUseCase,
    val castDetailsUseCase: CastDetailsUseCase,
    val moviesOfCastUseCase: MoviesOfCastUseCase,
    val castProfilesUseCase: CastProfilesUseCase
)
