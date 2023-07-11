package com.kyawzinlinn.moviesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kyawzinlinn.moviesapp.data.remote.MovieApi
import com.kyawzinlinn.moviesapp.data.remote.dto.Genre
import com.kyawzinlinn.moviesapp.data.remote.dto.Movie
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.PopularMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.SimilarMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TagMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.TopRatedMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.dto.UpComingMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.repository.MovieRepositoryImpl
import com.kyawzinlinn.moviesapp.databinding.ActivitySeeAllMoviesBinding
import com.kyawzinlinn.moviesapp.domain.adapter.VerticalMovieItemAdapter
import com.kyawzinlinn.moviesapp.presentation.now_playing_movie.MovieViewModel
import com.kyawzinlinn.moviesapp.utils.GENRE_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MOVIE_TYPE_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.MovieType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SeeAllMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeeAllMoviesBinding
    private lateinit var viewModel: MovieViewModel
    private var isLoading = false
    private var currentPage = 1
    private lateinit var adapter: VerticalMovieItemAdapter
    private lateinit var type: MovieType
    private lateinit var movieId: String
    private lateinit var genreId: String
    @Inject lateinit var repositoryImpl: MovieRepositoryImpl

    @Inject lateinit var api: MovieApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeeAllMoviesBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        bindUI()
        loadInitialMovies()
        setUpClickListeners()
        setUpSeeAllMoviesRecyclerview()
    }

    private fun loadInitialMovies() {
        viewModel.apply {
            getNowPlayingMovies("1")
            getPopularMovies("1")
            getTopRatedMovies("1")
            getUpComingMovies("1")
            try {
                getSimilarMovies(movieId,"1")
            }catch (e: Exception){}
        }
    }

    private fun setUpClickListeners() {
        binding.apply {
            ivSeeAllBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun bindUI() {
        type = intent?.extras?.get(MOVIE_TYPE_INTENT_EXTRA) as MovieType

        try {
            movieId = intent?.extras?.getString(MOVIE_ID_INTENT_EXTRA).toString()
        }catch (e: Exception){}

        var title = ""
        val movies = mutableListOf<Movie>()

        when(type){
            MovieType.NOW_PLAYING -> {
                title = "Now Playing Movies"
                viewModel.nowPlayingMovieState.observe(this){
                    binding.isLoading = it.isLoading
                    if (!it.isLoading) {
                        binding.movies = (it.data as NowPlayingMoviesDto).results
                        movies.clear()
                        movies.addAll((it.data as NowPlayingMoviesDto).results)
                    }
                }
            }
            MovieType.POPULAR -> {
            title = "Popular Movies"
                viewModel.popularMovieState.observe(this){
                    binding.isLoading = it.isLoading
                    if (!it.isLoading) {
                        binding.movies = (it.data as PopularMoviesDto)?.results
                        movies.clear()
                        movies.addAll((it.data as PopularMoviesDto).results)
                    }
                }
            }
            MovieType.TOP_RATED -> {
                title = "Top Rated Movies"
                viewModel.topRatedMovieState.observe(this){
                    binding.isLoading = it.isLoading
                    if (!it.isLoading) {
                        binding.movies = (it.data as TopRatedMoviesDto).results
                        movies.clear()
                        movies.addAll((it.data as TopRatedMoviesDto).results)
                    }
                }
            }
            MovieType.UPCOMING -> {
                title = "Upcoming Movies"
                viewModel.upComingMovieState.observe(this){
                    binding.isLoading = it.isLoading
                    if (!it.isLoading) {
                        binding.movies = (it.data as UpComingMoviesDto).results
                        movies.clear()
                        movies.addAll((it.data as UpComingMoviesDto).results)
                    }
                }
            }

            MovieType.SIMILAR -> {
                title = "Similar Movies"
                viewModel.similarMoviesState.observe(this){
                    binding.isLoading = it.isLoading
                    if (!it.isLoading) {
                        binding.movies = (it.data as SimilarMoviesDto).results
                        movies.clear()
                        movies.addAll((it.data as SimilarMoviesDto).results)
                    }
                }
            }

            MovieType.TAG_MOVIES -> {
                val genre = intent?.extras?.getSerializable(GENRE_INTENT_EXTRA) as Genre
                genreId = genre.id
                title = genre.name

                viewModel.getTagMovies(genreId,currentPage.toString())
                viewModel.tagMoviesState.observe(this){
                    binding.isLoading = it.isLoading
                    if (!it.isLoading) {

                        binding.movies = (it.data as TagMoviesDto).results
                        movies.clear()
                        movies.addAll((it.data as TagMoviesDto).results)
                    }
                }
            }
        }
        adapter = VerticalMovieItemAdapter(movies){
            goToMovieDetailsActivity(it)
        }

        binding.tvSeeAllMoviesTitle.text = title

    }

    private fun goToMovieDetailsActivity(movieId: String) {
        val intent = Intent(this,MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID_INTENT_EXTRA,movieId)
        startActivity(intent)
    }

    private fun setUpSeeAllMoviesRecyclerview() {
        binding.rvSeeAllMovies.adapter = adapter
        binding.rvSeeAllMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.rvSeeAllMovies.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    // Load more data
                    binding.pbLoadMore.visibility = View.VISIBLE
                    loadMoreData()
                }
            }
        })
    }

    private fun loadMoreData() {
        isLoading = true

        when(type){
            MovieType.NOW_PLAYING -> loadMoreNowPlayingMovies()

            MovieType.POPULAR -> loadMorePopularMovies()

            MovieType.TOP_RATED -> loadMoreTopRatedMovies()

            MovieType.UPCOMING -> loadMoreUpcomingMovies()

            MovieType.SIMILAR -> loadMoreSimilarMovies()

            MovieType.TAG_MOVIES -> loadMoreTagMovies()
        }

    }

    private fun loadMoreNowPlayingMovies() {
        currentPage++
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositoryImpl.getNowPlayingMovies(currentPage.toString())
            if (response.results != null){
                val newMovies = response.results
                withContext(Dispatchers.Main){
                    Handler().postDelayed(Runnable {
                        adapter.addMovies(newMovies)
                        binding.pbLoadMore.visibility = View.GONE
                    },500)
                }
            }
        }
    }

    private fun loadMorePopularMovies() {
        currentPage++
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositoryImpl.getPopularMovies(currentPage.toString())
            if (response.results != null){
                val newMovies = response.results
                withContext(Dispatchers.Main){
                    Handler().postDelayed(Runnable {
                        adapter.addMovies(newMovies)
                        binding.pbLoadMore.visibility = View.GONE
                    },500)
                }
            }
        }
    }

    private fun loadMoreTopRatedMovies() {
        currentPage++
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositoryImpl.getTopRatedMovies(currentPage.toString())
            if (response.results != null){
                val newMovies = response.results
                withContext(Dispatchers.Main){
                    Handler().postDelayed(Runnable {
                        adapter.addMovies(newMovies)
                        binding.pbLoadMore.visibility = View.GONE
                    },500)
                }
            }
        }
    }

    private fun loadMoreUpcomingMovies() {

        currentPage++
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositoryImpl.getUpComingMovies(currentPage.toString())
            if (response.results != null){
                val newMovies = response.results
                withContext(Dispatchers.Main){
                    Handler().postDelayed(Runnable {
                        adapter.addMovies(newMovies)
                        binding.pbLoadMore.visibility = View.GONE
                    },500)
                }
            }
        }
    }

    private fun loadMoreSimilarMovies(){
        currentPage++
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositoryImpl.getSimilarMovies(movieId,currentPage.toString())
            if (response.results != null){
                val newMovies = response.results
                withContext(Dispatchers.Main){
                    Handler().postDelayed(Runnable {
                        //movies.addAll(newMovies)
                        adapter.addMovies(newMovies)
                        binding.pbLoadMore.visibility = View.GONE
                    },500)
                }
            }
        }
    }

    private fun loadMoreTagMovies(){
        currentPage++
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositoryImpl.getMoviesByTagName(movieId,currentPage.toString())
            if (response.results != null){
                val newMovies = response.results
                withContext(Dispatchers.Main){
                    Handler().postDelayed(Runnable {
                        //movies.addAll(newMovies)
                        adapter.addMovies(newMovies)
                        binding.pbLoadMore.visibility = View.GONE
                    },500)
                }
            }
        }
    }
}