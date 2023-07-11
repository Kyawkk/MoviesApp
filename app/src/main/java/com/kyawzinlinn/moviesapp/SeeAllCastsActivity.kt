package com.kyawzinlinn.moviesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.data.remote.dto.CastsDto
import com.kyawzinlinn.moviesapp.databinding.ActivitySeeAllCastsBinding
import com.kyawzinlinn.moviesapp.domain.adapter.MovieCastItemAdapter
import com.kyawzinlinn.moviesapp.presentation.now_playing_movie.CastViewModel
import com.kyawzinlinn.moviesapp.utils.CAST_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllCastsActivity : AppCompatActivity() {
    private lateinit var castViewModel: CastViewModel
    private lateinit var binding: ActivitySeeAllCastsBinding
    private lateinit var movieId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeeAllCastsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        movieId = intent?.extras?.getString(MOVIE_ID_INTENT_EXTRA).toString()

        castViewModel = ViewModelProvider(this).get(CastViewModel::class.java)
        castViewModel.getMovieCasts(movieId)

        binding.lifecycleOwner = this
        binding.castViewModel = castViewModel

        setUpRecyclerView()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.apply {
            ivSeeAllBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvSeeAllCasts.adapter = MovieCastItemAdapter{
            val intent = Intent(this,CastDetailActivity::class.java)
            intent.putExtra(CAST_ID_INTENT_EXTRA,it.id.toString())
            startActivity(intent)
        }
    }
}