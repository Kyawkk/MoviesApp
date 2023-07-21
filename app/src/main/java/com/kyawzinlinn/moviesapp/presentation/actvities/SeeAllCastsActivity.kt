package com.kyawzinlinn.moviesapp.presentation.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.ViewModelProvider
import com.kyawzinlinn.moviesapp.databinding.ActivitySeeAllCastsBinding
import com.kyawzinlinn.moviesapp.presentation.adapter.MovieCastItemAdapter
import com.kyawzinlinn.moviesapp.presentation.viewmodel.CastViewModel
import com.kyawzinlinn.moviesapp.utils.CAST_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.ConnectionReceiver
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.TransitionName
import com.kyawzinlinn.moviesapp.utils.setUpLayoutTransition
import com.kyawzinlinn.moviesapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllCastsActivity : AppCompatActivity(),ConnectionReceiver.ConnectionReceiverListener {
    private lateinit var castViewModel: CastViewModel
    private lateinit var binding: ActivitySeeAllCastsBinding
    private lateinit var movieId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeeAllCastsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        movieId = intent?.extras?.getString(MOVIE_ID_INTENT_EXTRA).toString()
        castViewModel = ViewModelProvider(this).get(CastViewModel::class.java)

        // smooth layout transition
        binding.parent.setUpLayoutTransition()

        loadCasts()

        binding.lifecycleOwner = this
        binding.castViewModel = castViewModel

        setUpRecyclerView()
        setUpClickListeners()
    }

    fun loadCasts(){
        castViewModel.getMovieCasts(movieId)
        castViewModel.movieCastsState.observe(this){
            if (it.error.isNotEmpty()) showSnackBar(window.decorView,it.error){
                loadCasts()
            }
        }
    }

    override fun onConnectionChanged(isConnected: Boolean) {
        if (isConnected) loadCasts()
    }

    private fun setUpClickListeners() {
        binding.apply {
            ivSeeAllBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvSeeAllCasts.adapter = MovieCastItemAdapter{ cast ->
            val intent = Intent(this, CastDetailActivity::class.java)
            intent.putExtra(CAST_ID_INTENT_EXTRA,cast.id.toString())
            startActivity(intent)
        }
    }
}