package com.kyawzinlinn.moviesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.data.remote.dto.MovieDetailsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.databinding.ActivityMainBinding
import com.kyawzinlinn.moviesapp.domain.adapter.HorizontalMovieItemAdapter
import com.kyawzinlinn.moviesapp.presentation.now_playing_movie.MovieViewModel
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieViewModel
    private val TAG = "TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.apply {
            rvNowPlaying.adapter = getHorizontalMovieAdapter()
            rvPopular.adapter = getHorizontalMovieAdapter()
            rvTopRated.adapter = getHorizontalMovieAdapter()
            rvUpcoming.adapter = getHorizontalMovieAdapter()
        }
    }

    private fun getHorizontalMovieAdapter(): HorizontalMovieItemAdapter{
        return HorizontalMovieItemAdapter{
            val intent = Intent(this,MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_ID_INTENT_EXTRA,it)
            startActivity(intent)
        }
    }
}