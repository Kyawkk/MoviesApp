package com.kyawzinlinn.moviesapp.presentation.now_playing_movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.moviesapp.domain.use_case.cast.CastUseCase
import com.kyawzinlinn.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    private val castUseCase: CastUseCase
): ViewModel() {

    private val _movieCastsState = MutableLiveData<MovieState>()
    val movieCastsState = _movieCastsState

    private val _castDetailsState = MutableLiveData<MovieState>()
    val castDetailsState = _castDetailsState

    private val _moviesOfCastState = MutableLiveData<MovieState>()
    val moviesOfCastState = _moviesOfCastState

    private val _castProfilesState = MutableLiveData<MovieState>()
    val castProfilesState = _castProfilesState
    fun getMovieCasts(movieId: String){
        castUseCase.castsUseCase(movieId).onEach {
            when(it){
                is Resource.Loading -> _movieCastsState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _movieCastsState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _movieCastsState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getCastDetails(castId: String){
        castUseCase.castDetailsUseCase(castId).onEach {
            when(it){
                is Resource.Loading -> _castDetailsState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _castDetailsState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _castDetailsState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMoviesOfCast(castId: String){
        castUseCase.moviesOfCastUseCase(castId).onEach {
            when(it){
                is Resource.Loading -> _moviesOfCastState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _moviesOfCastState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _moviesOfCastState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getCastProfiles(castId: String){
        castUseCase.castProfilesUseCase(castId).onEach {
            when(it){
                is Resource.Loading -> _castProfilesState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _castProfilesState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _castProfilesState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}