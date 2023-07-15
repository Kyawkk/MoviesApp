package com.kyawzinlinn.moviesapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.moviesapp.domain.use_case.movie.MovieUseCase
import com.kyawzinlinn.moviesapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {

    private val _nowPlayingMovieState = MutableLiveData<MovieState>()
    val nowPlayingMovieState = _nowPlayingMovieState

    private val _popularMovieState = MutableLiveData<MovieState>()
    val popularMovieState = _popularMovieState

    private val _topRatedMovieState = MutableLiveData<MovieState>()
    val topRatedMovieState = _topRatedMovieState

    private val _upComingMovieState = MutableLiveData<MovieState>()
    val upComingMovieState = _upComingMovieState

    private val _movieDetailState = MutableLiveData<MovieState>()
    val movieDetailState = _movieDetailState

    private val _similarMoviesState = MutableLiveData<MovieState>()
    val similarMoviesState = _similarMoviesState

    private val _tagMoviesState = MutableLiveData<MovieState>()
    val tagMoviesState = _tagMoviesState

    private val _searchMoviesState = MutableLiveData<MovieState>()
    val searchMoviesState = _searchMoviesState

    fun getNowPlayingMovies(page: String){
        movieUseCase.nowPlayingMovieUseCase(page).onEach {
            when(it){
                is Resource.Loading -> _nowPlayingMovieState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _nowPlayingMovieState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _nowPlayingMovieState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getPopularMovies(page: String){
        movieUseCase.popularMovieUseCase(page).onEach {
            when(it){
                is Resource.Loading -> _popularMovieState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _popularMovieState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _popularMovieState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getTopRatedMovies(page: String){
        movieUseCase.topRateMovieUseCase(page).onEach {
            when(it){
                is Resource.Loading -> _topRatedMovieState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _topRatedMovieState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _topRatedMovieState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUpComingMovies(page: String){
        movieUseCase.upComingMovieUseCase(page).onEach {
            when(it){
                is Resource.Loading -> _upComingMovieState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _upComingMovieState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _upComingMovieState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMovieDetails(movieId: String){
        movieUseCase.movieDetailsUseCase(movieId).onEach {
            when(it){
                is Resource.Loading -> _movieDetailState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _movieDetailState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _movieDetailState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSimilarMovies(movieId: String,page: String){
        movieUseCase.similarMoviesUseCase(movieId,page).onEach {
            when(it){
                is Resource.Loading -> _similarMoviesState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _similarMoviesState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _similarMoviesState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getTagMovies(genreId: String, page: String){
        movieUseCase.tagMoviesUseCase(genreId,page).onEach {
            when(it){
                is Resource.Loading -> _tagMoviesState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _tagMoviesState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _tagMoviesState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getSearchMovies(query: String, page: String){
        movieUseCase.searchMoviesUseCase(query,page).onEach {
            when(it){
                is Resource.Loading -> _searchMoviesState.value = MovieState(isLoading = true)
                is Resource.Success -> {
                    _searchMoviesState.value = MovieState(data = it.data)
                }
                is Resource.Error -> {
                    _searchMoviesState.value = MovieState(error = it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

}