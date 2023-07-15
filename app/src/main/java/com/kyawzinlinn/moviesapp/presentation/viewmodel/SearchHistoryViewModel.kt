package com.kyawzinlinn.moviesapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.moviesapp.data.local.dao.SearchDao
import com.kyawzinlinn.moviesapp.data.local.database.MovieSearchHistory
import com.kyawzinlinn.moviesapp.data.remote.repository.SearchHistoryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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