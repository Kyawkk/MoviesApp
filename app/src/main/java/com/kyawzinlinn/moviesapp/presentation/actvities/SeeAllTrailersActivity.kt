package com.kyawzinlinn.moviesapp.presentation.actvities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.databinding.ActivitySeeAllTrailersBinding
import com.kyawzinlinn.moviesapp.domain.adapter.TrailerItemAdapter
import com.kyawzinlinn.moviesapp.presentation.viewmodel.MovieViewModel
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_NAME_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.RecyclerviewType
import com.kyawzinlinn.moviesapp.utils.playYouTubeVideo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllTrailersActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeeAllTrailersBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeeAllTrailersBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        setContentView(binding.root)

        setUpClickListeners()
        setUpTrailersRecyclerView()
    }

    private fun setUpClickListeners() {
        binding.apply {
            ivTrailersBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun setUpTrailersRecyclerView() {
        val movieId = intent?.extras?.getString(MOVIE_ID_INTENT_EXTRA).toString()
        val movieName = intent?.extras?.getString(MOVIE_NAME_INTENT_EXTRA).toString()

        viewModel.getMovieTrailers(movieId)

        binding.viewmodel = viewModel
        binding.title = movieName
        binding.lifecycleOwner = this

        binding.rvSeeAllTrailers.adapter = TrailerItemAdapter(RecyclerviewType.VERTICAL){
            playYouTubeVideo(it)
        }
    }
}