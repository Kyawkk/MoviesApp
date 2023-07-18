package com.kyawzinlinn.moviesapp.presentation.actvities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.R
import com.kyawzinlinn.moviesapp.databinding.ActivityMoviesByCastBinding
import com.kyawzinlinn.moviesapp.presentation.viewmodel.CastViewModel
import com.kyawzinlinn.moviesapp.presentation.viewmodel.MovieViewModel
import com.kyawzinlinn.moviesapp.utils.CAST_ID_INTENT_EXTRA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesByCastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesByCastBinding
    private lateinit var viewModel: CastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesByCastBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(CastViewModel::class.java)

        setContentView(binding.root)

        val castId = intent?.extras?.getString(CAST_ID_INTENT_EXTRA).toString()

        viewModel.getMoviesOfCast(castId)

        setUpCastMoviesRecyclerView()
    }

    private fun setUpCastMoviesRecyclerView() {

    }
}