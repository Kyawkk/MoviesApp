package com.kyawzinlinn.moviesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.moviesapp.data.local.database.MovieSearchHistory
import com.kyawzinlinn.moviesapp.data.remote.repository_impl.SearchHistoryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(
    private val searchHistoryRepositoryImpl: SearchHistoryRepositoryImpl
): ViewModel() {

    val allSearchHistory = searchHistoryRepositoryImpl.getAllSearches().asLiveData()

    fun addMovieSearchHistory(movieSearchHistory: MovieSearchHistory){
        viewModelScope.launch { searchHistoryRepositoryImpl.addSearchHistory(movieSearchHistory) }
    }

    fun deleteAllSearchHistory(){
        viewModelScope.launch { searchHistoryRepositoryImpl.deleteAllSearches() }
    }
}