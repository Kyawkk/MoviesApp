package com.kyawzinlinn.moviesapp.presentation.actvities

import android.animation.LayoutTransition
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.databinding.ActivityMainBinding
import com.kyawzinlinn.moviesapp.domain.adapter.HorizontalMovieItemAdapter
import com.kyawzinlinn.moviesapp.presentation.viewmodel.MovieViewModel
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_TYPE_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MovieType
import com.kyawzinlinn.moviesapp.utils.openGitHub
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

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        // set data to binding
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.apply {
            getNowPlayingMovies("1")
            getPopularMovies("1")
            getTopRatedMovies("1")
            getUpComingMovies("1")
        }

        binding.apply {
            rvNowPlaying.adapter = getHorizontalMovieAdapter()
            rvPopular.adapter = getHorizontalMovieAdapter()
            rvTopRated.adapter = getHorizontalMovieAdapter()
            rvUpcoming.adapter = getHorizontalMovieAdapter()
        }

        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.apply {
            tvSeeAllNowPlaying.setOnClickListener { goToSeeAllMoviesActivity(MovieType.NOW_PLAYING) }
            tvSeeAllPopular.setOnClickListener { goToSeeAllMoviesActivity(MovieType.POPULAR) }
            tvSeeAllTopRated.setOnClickListener { goToSeeAllMoviesActivity(MovieType.TOP_RATED) }
            tvSeeAllUpcoming.setOnClickListener { goToSeeAllMoviesActivity(MovieType.UPCOMING) }

            ivSearch.setOnClickListener { goToSearchMoviesActivity() }
            ivGithub.setOnClickListener { openGitHub(this@MainActivity) }
        }
    }

    private fun goToSearchMoviesActivity() {
        val intent = Intent(this, SearchMoviesActivity::class.java)
        startActivity(intent)
    }

    private fun goToSeeAllMoviesActivity(movieType: MovieType) {
        val intent = Intent(this, SeeAllMoviesActivity::class.java)
        intent.putExtra(MOVIE_TYPE_INTENT_EXTRA,movieType)
        startActivity(intent)
    }

    private fun getHorizontalMovieAdapter(): HorizontalMovieItemAdapter{
        return HorizontalMovieItemAdapter{ id, itemBinding ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            val pair1 = Pair.create(itemBinding.ivMoviePoster as View,itemBinding.ivMoviePoster.transitionName)
            val pair2 = Pair.create(itemBinding.textView7 as View,itemBinding.textView7.transitionName)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,pair1,pair2)
            intent.putExtra(MOVIE_ID_INTENT_EXTRA,id)
            startActivity(intent,options.toBundle())
        }
    }
}