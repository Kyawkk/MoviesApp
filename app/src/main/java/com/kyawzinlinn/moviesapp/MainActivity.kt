package com.kyawzinlinn.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.databinding.ActivityMainBinding
import com.kyawzinlinn.moviesapp.presentation.now_playing_movie.NowPlayingMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NowPlayingMovieViewModel
    private val TAG = "TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NowPlayingMovieViewModel::class.java)

        viewModel.state.observe(this){
            Log.d(TAG, it.isLoading.toString())
            Log.d(TAG, "${it.nowPlayingMovies}")
            Log.d(TAG, "${it.error}")
        }

    }
}