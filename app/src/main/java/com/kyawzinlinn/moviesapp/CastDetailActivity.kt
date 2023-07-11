package com.kyawzinlinn.moviesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.data.remote.dto.CastProfilesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.Movie
import com.kyawzinlinn.moviesapp.data.remote.dto.MoviesOfCastDto
import com.kyawzinlinn.moviesapp.databinding.ActivityCastDetailBinding
import com.kyawzinlinn.moviesapp.domain.adapter.HorizontalMovieItemAdapter
import com.kyawzinlinn.moviesapp.presentation.now_playing_movie.CastViewModel
import com.kyawzinlinn.moviesapp.utils.CAST_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CastDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCastDetailBinding
    private lateinit var viewModel: CastViewModel
    private var castId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCastDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(CastViewModel::class.java)

        setContentView(binding.root)

        binding.castViewModel = viewModel
        binding.lifecycleOwner = this

        // retrieve cast id from intent
        castId = intent?.extras?.getString(CAST_ID_INTENT_EXTRA).toString()

        viewModel.apply {
            getCastDetails(castId)
            getMoviesOfCast(castId)
            getCastProfiles(castId)
        }
        
        viewModel.castProfilesState.observe(this){
            if (!it.isLoading) Log.d("TAG", "onCreate: ${(it.data as CastProfilesDto).profiles.map { it.file_path }}")
        }

        setUpClickListeners()
        setUpMoviesOfCastRecyclerView()
    }

    private fun setUpClickListeners() {
        binding.apply {
            ivCastDetailBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun setUpMoviesOfCastRecyclerView() {
        binding.rvMoviesOfCast.adapter = HorizontalMovieItemAdapter{id, itemBinding ->
            val intent = Intent(this,MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_ID_INTENT_EXTRA,id)
            startActivity(intent)
        }
    }
}