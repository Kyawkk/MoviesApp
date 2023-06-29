package com.kyawzinlinn.moviesapp.presentation.now_playing_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.moviesapp.domain.use_case.NowPlayingMovieUseCase
import com.kyawzinlinn.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NowPlayingMovieViewModel @Inject constructor(
    private val nowPlayingMovieUseCase: NowPlayingMovieUseCase
): ViewModel() {
    private val _state = MutableLiveData<NowPlayingMovieState>()
    val state = _state

    init {
        getNowPlayingMovies()
    }

    fun getNowPlayingMovies(){
        nowPlayingMovieUseCase().onEach {
            when(it){
                is Resource.Loading -> _state.value = NowPlayingMovieState(isLoading = true)
                is Resource.Success -> {
                    _state.value = NowPlayingMovieState(nowPlayingMovies = it.data)
                }
                is Resource.Error -> {
                    _state.value = NowPlayingMovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}