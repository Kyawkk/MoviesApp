package com.kyawzinlinn.moviesapp.presentation.actvities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.data.remote.dto.MovieDetailsDto
import com.kyawzinlinn.moviesapp.databinding.ActivityMovieDetailBinding
import com.kyawzinlinn.moviesapp.presentation.adapter.GenreAdapter
import com.kyawzinlinn.moviesapp.presentation.adapter.HorizontalMovieItemAdapter
import com.kyawzinlinn.moviesapp.presentation.adapter.TrailerItemAdapter
import com.kyawzinlinn.moviesapp.presentation.adapter.MovieCastAvatarItemAdapter
import com.kyawzinlinn.moviesapp.presentation.viewmodel.CastViewModel
import com.kyawzinlinn.moviesapp.presentation.viewmodel.MovieViewModel
import com.kyawzinlinn.moviesapp.utils.CAST_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.ConnectionReceiver
import com.kyawzinlinn.moviesapp.utils.GENRE_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_NAME_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_TYPE_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MovieType
import com.kyawzinlinn.moviesapp.utils.RecyclerviewType
import com.kyawzinlinn.moviesapp.utils.TransitionName
import com.kyawzinlinn.moviesapp.utils.playYouTubeVideo
import com.kyawzinlinn.moviesapp.utils.setUpLayoutTransition
import com.kyawzinlinn.moviesapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity(), ConnectionReceiver.ConnectionReceiverListener {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var castViewModel: CastViewModel
    private lateinit var movieId: String
    private lateinit var movieName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        castViewModel = ViewModelProvider(this).get(CastViewModel::class.java)

        setContentView(binding.root)

        movieId = intent?.extras?.getString(MOVIE_ID_INTENT_EXTRA).toString()

        binding.apply {
            materialCardView.transitionName = "${TransitionName.ITEM_IMAGE_TRANSITION_NAME}$movieId"
            tvMovieTitle.transitionName = "${TransitionName.ITEM_TEXT_TRANSITION_NAME}$movieId"
        }

        setUpLayoutTransition(binding.parent)

        setUpClickListeners()
        loadMovieDetail()
        loadMovieCasts()

        // set data to binding
        binding.castViewModel = castViewModel
        binding.movieViewModel = movieViewModel
        binding.lifecycleOwner = this

        bindUI()
    }

    override fun onConnectionChanged(isConnected: Boolean) {
        if(isConnected) {
            loadMovieDetail()
            loadMovieCasts()
        }
    }

    private fun setUpClickListeners(){
        binding.apply {
            ivMovieDetailBack.setOnClickListener { onBackPressed() }

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

            // go to see all trailers activity
            tvSeeAllTrailers.setOnClickListener { goToSeeAllTrailersActivity() }

            // not found layout button action
            notFoundLayout.setOnTryAgainListener {
                loadMovieDetail()
                loadMovieCasts()

                notFoundLayout.visibility = View.GONE
            }
        }
    }

    private fun goToSeeAllTrailersActivity() {
        val intent = Intent(this,SeeAllTrailersActivity::class.java)
        intent.putExtra(MOVIE_ID_INTENT_EXTRA,movieId)
        intent.putExtra(MOVIE_NAME_INTENT_EXTRA,movieName)
        startActivity(intent)
    }

    private fun bindUI() {

        //hideAllNotFoundLayouts()

        setUpGenreAdapter()
        setUpCastRecyclerviewAdapter()
        setUpSimilarMoviewRecyclerviewAdapter()
        setUpTrailerRecyclerviewAdapter()

        movieViewModel.movieDetailState.observe(this){
            if (it.error.isNotEmpty() || it.error.isNotBlank()) {
                binding.notFoundLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpTrailerRecyclerviewAdapter() {
        binding.rvTrailers.adapter = TrailerItemAdapter(RecyclerviewType.HORIZONTAL) {
            playYouTubeVideo(it)
        }
    }

    private fun setUpSimilarMoviewRecyclerviewAdapter() {
        binding.rvSimilarMovies.adapter = HorizontalMovieItemAdapter { movieId, itemBinding ->
            val intent = Intent(this, MovieDetailActivity::class.java)

            itemBinding.ivMoviePoster.transitionName =
                "${TransitionName.ITEM_IMAGE_TRANSITION_NAME}$movieId"
            itemBinding.textView7.transitionName =
                "${TransitionName.ITEM_TEXT_TRANSITION_NAME}$movieId"

            val pair1 = Pair.create(
                itemBinding.ivMoviePoster as View,
                itemBinding.ivMoviePoster.transitionName
            )
            val pair2 =
                Pair.create(itemBinding.textView7 as View, itemBinding.textView7.transitionName)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2)
            intent.putExtra(MOVIE_ID_INTENT_EXTRA, movieId)
            startActivity(intent, options.toBundle())
        }
    }

    private fun setUpCastRecyclerviewAdapter() {
        binding.rvCast.adapter = MovieCastAvatarItemAdapter { castId, view ->
            val intent = Intent(this, CastDetailActivity::class.java)

            view.transitionName = "${TransitionName.CAST_IMAGE_TRANSITION_NAME}$castId"

            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair(view, view.transitionName)
            )
            intent.putExtra(CAST_ID_INTENT_EXTRA, castId)
            startActivity(intent, optionsCompat.toBundle())
        }
    }

    private fun setUpGenreAdapter() {
        binding.rvGenre.adapter = GenreAdapter {
            // show all movies that appropriate with genre
            val intent = Intent(this, SeeAllMoviesActivity::class.java)
            intent.putExtra(MOVIE_TYPE_INTENT_EXTRA, MovieType.TAG_MOVIES)
            intent.putExtra(GENRE_INTENT_EXTRA, it)
            startActivity(intent)
        }
    }

    private fun loadMovieDetail() {
        movieViewModel.apply {
            getMovieDetails(movieId)
            getSimilarMovies(movieId,"1")
            getMovieTrailers(movieId)
        }

        movieViewModel.movieDetailState.observe(this){
            if (it.data != null) movieName = (it.data as MovieDetailsDto).title
            if (it.error.isNotEmpty()) showSnackBar(window.decorView,it.error,{
                loadMovieDetail()
                loadMovieCasts()
            })
        }
    }

    private fun loadMovieCasts(){
        castViewModel.apply {
            getMovieCasts(movieId)
        }
    }
}