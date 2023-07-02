package com.kyawzinlinn.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.databinding.ActivityMovieDetailBinding
import com.kyawzinlinn.moviesapp.presentation.now_playing_movie.MovieViewModel
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        setContentView(binding.root)

        loadMovieDetail()

    }

    private fun loadMovieDetail() {
        val movieId = intent?.extras?.getString(MOVIE_ID_INTENT_EXTRA)
        viewModel.apply {
            getMovieDetails(movieId!!)
            movieDetailState.observe(this@MovieDetailActivity){
                Log.d(TAG, "loadMovieDetail: ${it.data}")
            }
        }
    }
}