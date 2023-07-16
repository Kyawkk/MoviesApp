package com.kyawzinlinn.moviesapp.presentation.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.kyawzinlinn.moviesapp.data.remote.dto.CastProfilesDto
import com.kyawzinlinn.moviesapp.databinding.ActivityCastDetailBinding
import com.kyawzinlinn.moviesapp.domain.adapter.HorizontalMovieItemAdapter
import com.kyawzinlinn.moviesapp.presentation.viewmodel.CastViewModel
import com.kyawzinlinn.moviesapp.utils.CAST_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.setUpLayoutTransition
import com.kyawzinlinn.moviesapp.utils.showSnackBar
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

        loadAllCastDetails()

        setUpLayoutTransition(binding.parent)
        setUpClickListeners()
        setUpMoviesOfCastRecyclerView()
    }

    private fun loadAllCastDetails() {
        viewModel.apply {
            getCastDetails(castId)
            getMoviesOfCast(castId)
            getCastProfiles(castId)
        }
    }

    private fun setUpClickListeners() {
        binding.apply {
            ivCastDetailBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun setUpMoviesOfCastRecyclerView() {
        binding.rvMoviesOfCast.adapter = HorizontalMovieItemAdapter{id, itemBinding ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_ID_INTENT_EXTRA,id)
            startActivity(intent)
        }

        viewModel.castDetailsState.observe(this){
            if (it.error.isNotEmpty()) showSnackBar(window.decorView.rootView,it.error){
                loadAllCastDetails()
            }
        }
    }
}