package com.kyawzinlinn.moviesapp.presentation.actvities

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.R
import com.kyawzinlinn.moviesapp.databinding.ActivityMovieDetailBinding
import com.kyawzinlinn.moviesapp.domain.adapter.GenreAdapter
import com.kyawzinlinn.moviesapp.domain.adapter.HorizontalMovieItemAdapter
import com.kyawzinlinn.moviesapp.domain.adapter.MovieCastAvatarItemAdapter
import com.kyawzinlinn.moviesapp.presentation.viewmodel.CastViewModel
import com.kyawzinlinn.moviesapp.presentation.viewmodel.MovieViewModel
import com.kyawzinlinn.moviesapp.utils.CAST_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.GENRE_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_TYPE_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MovieType
import com.kyawzinlinn.moviesapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var castViewModel: CastViewModel
    private lateinit var movieId: String
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        castViewModel = ViewModelProvider(this).get(CastViewModel::class.java)

        setContentView(binding.root)

        binding.root.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        setUpClickListeners()
        loadMovieDetail()
        loadMovieCasts()

        // set data to binding
        binding.castViewModel = castViewModel
        binding.movieViewModel = movieViewModel
        binding.lifecycleOwner = this

        bindUI()
    }

    private fun setUpClickListeners(){
        binding.apply {
            ivMovieDetailBack.setOnClickListener { onBackPressed() }
            ivMovieFavorite.setOnClickListener {
                isFavorite = !isFavorite
                if (isFavorite) ivMovieFavorite.setImageResource(R.drawable.baseline_favorite_24)
                else ivMovieFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            }

            // go to see all movie activity
            tvSeeAllSimilarMovies.setOnClickListener {
                val intent = Intent(this@MovieDetailActivity, SeeAllMoviesActivity::class.java)
                intent.putExtra(MOVIE_ID_INTENT_EXTRA,movieId)
                intent.putExtra(MOVIE_TYPE_INTENT_EXTRA, MovieType.SIMILAR)
                startActivity(intent)
            }

            // go to see all casts activity
            tvSeeAllCasts.setOnClickListener {
                val intent = Intent(this@MovieDetailActivity, SeeAllCastsActivity::class.java)
                intent.putExtra(MOVIE_ID_INTENT_EXTRA,movieId)
                startActivity(intent)
            }
        }
    }

    private fun bindUI() {
        binding.rvGenre.adapter = GenreAdapter(){
            // show all movies that appropriate with genre
            val intent = Intent(this, SeeAllMoviesActivity::class.java)
            intent.putExtra(MOVIE_TYPE_INTENT_EXTRA,MovieType.TAG_MOVIES)
            intent.putExtra(GENRE_INTENT_EXTRA,it)
            startActivity(intent)
        }

        binding.rvCast.adapter = MovieCastAvatarItemAdapter{
            val intent = Intent(this, CastDetailActivity::class.java)
            intent.putExtra(CAST_ID_INTENT_EXTRA,it)
            startActivity(intent)
        }

        binding.rvSimilarMovies.adapter = HorizontalMovieItemAdapter{ movieId, itemBinding ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            val pair1 = Pair.create(itemBinding.ivMoviePoster as View,itemBinding.ivMoviePoster.transitionName)
            val pair2 = Pair.create(itemBinding.textView7 as View,itemBinding.textView7.transitionName)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,pair1,pair2)
            intent.putExtra(MOVIE_ID_INTENT_EXTRA,movieId)
            startActivity(intent,options.toBundle())
        }
    }

    private fun loadMovieDetail() {
        movieId = intent?.extras?.getString(MOVIE_ID_INTENT_EXTRA).toString()
        movieViewModel.getMovieDetails(movieId!!)
        movieViewModel.getSimilarMovies(movieId!!,"1")
        movieViewModel.movieDetailState.observe(this){
            if (it.error.isNotEmpty()) showSnackBar(window.decorView,it.error,{

            })
        }
    }

    private fun loadMovieCasts(){
        castViewModel.apply {
            getMovieCasts(movieId)
        }
    }
}