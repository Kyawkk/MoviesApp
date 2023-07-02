package com.kyawzinlinn.moviesapp.domain.binding_adapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kyawzinlinn.moviesapp.data.remote.dto.Movie
import com.kyawzinlinn.moviesapp.data.remote.dto.NowPlayingMoviesDto
import com.kyawzinlinn.moviesapp.domain.adapter.HorizontalMovieItemAdapter
import com.kyawzinlinn.moviesapp.utils.BASE_URL
import com.kyawzinlinn.moviesapp.utils.IMG_URL_PREFIX

const val TAG = "TAG"

@BindingAdapter("movies")
fun bindRecyclerView(recyclerView: RecyclerView, movies: List<Movie>?){
    val adapter = recyclerView.adapter as HorizontalMovieItemAdapter
    recyclerView.apply {
        setHasFixedSize(true)
        setAdapter(adapter)
    }

    adapter.submitList(movies)
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?){
    imgUrl?.let {
        imageView.load("$IMG_URL_PREFIX$it"){crossfade(true)}
    }
}