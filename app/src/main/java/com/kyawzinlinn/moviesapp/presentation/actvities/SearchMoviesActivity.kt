package com.kyawzinlinn.moviesapp.presentation.actvities

import android.animation.LayoutTransition
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.transition.Transition
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kyawzinlinn.moviesapp.data.local.database.MovieSearchHistory
import com.kyawzinlinn.moviesapp.data.remote.dto.SearchMoviesDto
import com.kyawzinlinn.moviesapp.data.remote.repository.MovieRepositoryImpl
import com.kyawzinlinn.moviesapp.databinding.ActivitySearchMoviesBinding
import com.kyawzinlinn.moviesapp.domain.adapter.SearchHistoryAdapter
import com.kyawzinlinn.moviesapp.domain.adapter.VerticalMovieItemAdapter
import com.kyawzinlinn.moviesapp.presentation.viewmodel.MovieViewModel
import com.kyawzinlinn.moviesapp.presentation.viewmodel.SearchHistoryViewModel
import com.kyawzinlinn.moviesapp.utils.MOVIE_ID_INTENT_EXTRA
import com.kyawzinlinn.moviesapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SearchMoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMoviesBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var searchHistoryViewModel: SearchHistoryViewModel
    private lateinit var adapter: VerticalMovieItemAdapter
    private var currentPage = 1
    private lateinit var query: String
    @Inject
    lateinit var repositoryImpl: MovieRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        searchHistoryViewModel = ViewModelProvider(this).get(SearchHistoryViewModel::class.java)

        setContentView(binding.root)

        binding.root.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        binding.viewModel = viewModel
        binding.searchHistoryViewModel = searchHistoryViewModel
        binding.lifecycleOwner = this

        setUpSearchHistoryRecyclerview()
        setUpClickListeners()
        showSearchSuggestions()
    }

    private fun showSearchSuggestions() {
        binding.edSearchMovies.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.getSearchSuggestions(query.toString())
                viewModel.searchSuggestionsMoviesState.observe(this@SearchMoviesActivity){
                    if (it.data != null){
                        val suggestions = (it.data as SearchMoviesDto).results.map{ it.title }.take(5)
                        val suggestionAdapter = ArrayAdapter(this@SearchMoviesActivity,android.R.layout.simple_list_item_1,suggestions)
                        binding.apply {
                            edSearchMovies.setAdapter(suggestionAdapter)
                            edSearchMovies.threshold = 1

                            edSearchMovies.setOnItemClickListener { adapterView, view, i, l ->
                                // store search history
                                val searchQuery = binding.edSearchMovies.text.toString()
                                if(searchQuery.isNotEmpty()) searchHistoryViewModel?.addMovieSearchHistory(MovieSearchHistory(0,searchQuery))

                                performSearch()
                            }
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun setUpClickListeners() {
        binding.apply {
            ivSearchBack.setOnClickListener { onBackPressed() }
            tvClearSearch.setOnClickListener { searchHistoryViewModel?.deleteAllSearchHistory() }
            edSearchMovies.setOnEditorActionListener { textView, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH){

                    // store search history
                    val searchQuery = binding.edSearchMovies.text.toString()
                    if(searchQuery.isNotEmpty()) searchHistoryViewModel?.addMovieSearchHistory(MovieSearchHistory(0,searchQuery))

                    performSearch()

                    true
                }
                else false
            }
        }
    }

    private fun performSearch() {
        // hide not found layout
        binding.notFoundLayout.visibility = View.GONE
        binding.rvSearchMovies.visibility = View.GONE

        query = binding.edSearchMovies.text.toString()

        currentPage = 1
        viewModel.getSearchMovies(query,currentPage.toString())
        setUpMoviesRecyclerView()
    }

    private fun setUpMoviesRecyclerView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.searchMoviesState.observe(this){
            if (it.data != null){
                binding.rvSearchMovies.visibility = View.VISIBLE

                val searchMovies = (it.data as SearchMoviesDto).results
                binding.totalResults = it.data.total_results.toString()

                binding.rvSearchMovies.setHasFixedSize(true)
                adapter = VerticalMovieItemAdapter(searchMovies.toMutableList()){
                    goToMovieDetailsActivity(it)
                }

                binding.rvSearchMovies.adapter = adapter
                binding.rvSearchMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager = binding.rvSearchMovies.layoutManager as LinearLayoutManager
                        if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1) {
                            // Load more data
                            binding.pbLoadMore.visibility = View.VISIBLE
                            loadMoreData()
                        }
                    }
                })
            }

            if (it.error.isNotEmpty()) showSnackBar(window.decorView,it.error,{
                performSearch()
            })
        }
    }

    private fun setUpSearchHistoryRecyclerview(){
        binding.rvSearchHistory.setHasFixedSize(true)
        binding.rvSearchHistory.adapter = SearchHistoryAdapter{
            binding.edSearchMovies.setText(it.name)
            performSearch()
        }
    }

    private fun goToMovieDetailsActivity(movieId: String) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID_INTENT_EXTRA,movieId)
        startActivity(intent)
    }

    private fun loadMoreData() {
        currentPage++
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repositoryImpl.getSearchMovies(query,currentPage.toString())
                if (response.results != null){
                    val newMovies = response.results
                    withContext(Dispatchers.Main){
                        Handler().postDelayed(Runnable {
                            adapter.addMovies(newMovies)
                            binding.pbLoadMore.visibility = View.GONE
                        },500)
                    }
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    binding.pbLoadMore.visibility = View.GONE
                }
            }
        }
    }
}