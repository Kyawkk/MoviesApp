package com.kyawzinlinn.moviesapp.domain.binding_adapter

import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.facebook.shimmer.ShimmerFrameLayout
import com.kyawzinlinn.moviesapp.data.local.database.MovieSearchHistory
import com.kyawzinlinn.moviesapp.data.remote.dto.Cast
import com.kyawzinlinn.moviesapp.data.remote.dto.Genre
import com.kyawzinlinn.moviesapp.data.remote.dto.Movie
import com.kyawzinlinn.moviesapp.data.remote.dto.Profile
import com.kyawzinlinn.moviesapp.domain.adapter.GenreAdapter
import com.kyawzinlinn.moviesapp.domain.adapter.HorizontalMovieItemAdapter
import com.kyawzinlinn.moviesapp.domain.adapter.ImageSliderAdapter
import com.kyawzinlinn.moviesapp.domain.adapter.MovieCastAvatarItemAdapter
import com.kyawzinlinn.moviesapp.domain.adapter.MovieCastItemAdapter
import com.kyawzinlinn.moviesapp.domain.adapter.SearchHistoryAdapter
import com.kyawzinlinn.moviesapp.domain.adapter.VerticalMovieItemAdapter
import com.kyawzinlinn.moviesapp.utils.CastType
import com.kyawzinlinn.moviesapp.utils.IMG_URL_PREFIX_300
import com.kyawzinlinn.moviesapp.utils.IMG_URL_PREFIX_500
import com.kyawzinlinn.moviesapp.utils.PosterSize
import com.kyawzinlinn.moviesapp.utils.RecyclerviewType
import com.kyawzinlinn.moviesapp.utils.calculateAge

const val TAG = "TAG"

@BindingAdapter("movies", "recyclerViewType")
fun bindRecyclerView(recyclerView: RecyclerView, movies: List<Any>?, type: RecyclerviewType){
    when (type){
        RecyclerviewType.HORIZONTAL -> {
            (recyclerView.adapter as HorizontalMovieItemAdapter).submitList(movies?.map { it as Movie })
        }
        RecyclerviewType.VERTICAL -> {
            if (!movies.isNullOrEmpty()){
                val adapter = recyclerView.adapter as VerticalMovieItemAdapter
                recyclerView.adapter = adapter
            }
        }
    }
}

@BindingAdapter("imageUrl","size")
fun bindImage(imageView: ImageView, imgUrl: String?, size: PosterSize){
    val url = when(size){
        PosterSize.W300 -> "$IMG_URL_PREFIX_300$imgUrl"
        PosterSize.W500 -> "$IMG_URL_PREFIX_500$imgUrl"
    }
    Log.d(TAG, "bindImage: $url")
    imgUrl?.let {
        imageView.load(url){crossfade(true)}
    }
}

@BindingAdapter("setAge")
fun bindCastAge(textView: TextView, dateOfBirth: String?){
    try {
        if (!dateOfBirth.isNullOrEmpty()) textView.text = "$dateOfBirth (${calculateAge(dateOfBirth)})"
    }catch (e: Exception){}
}

@BindingAdapter("splitDate")
fun splitDate(textView: TextView, string: String){
    textView.text = string.split("-").get(0)
}

@BindingAdapter("isShimmerLoading")
fun showShimmer(shimmerFrameLayout: ShimmerFrameLayout, isLoading: Boolean){
    if(isLoading) {
        shimmerFrameLayout.startShimmer()
        shimmerFrameLayout.visibility = View.VISIBLE
    }
    else {
        shimmerFrameLayout.stopShimmer()
        shimmerFrameLayout.visibility = View.INVISIBLE
    }
}

@BindingAdapter("genres")
fun bindGenreRecyclerview(recyclerView: RecyclerView, genres: List<Genre>?){
    val adapter = recyclerView.adapter as GenreAdapter
    recyclerView.apply {
        setHasFixedSize(true)
        setAdapter(adapter)
    }
    adapter.submitList(genres)
}

@BindingAdapter("searchHistories")
fun bindHistoryRecyclerview(recyclerView: RecyclerView, searchHistories: List<MovieSearchHistory>?){
    val adapter = recyclerView.adapter as SearchHistoryAdapter
    recyclerView.apply {
        setHasFixedSize(true)
        setAdapter(adapter)
    }
    adapter.submitList(searchHistories)
}

@BindingAdapter("sliderImages")
fun bindImageSlider(viewPager: ViewPager2, profiles: List<Profile>?){
    if(profiles != null && profiles.isNotEmpty()){
        val sliderAdapter = ImageSliderAdapter(profiles!!.map { it.file_path })
        viewPager.adapter = sliderAdapter

        val handler = Handler()

// Disable clipping and over-scroll mode
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.offscreenPageLimit = 4
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

// Set up page transformer
        val pageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }
        viewPager.setPageTransformer(pageTransformer)

// Auto-scroll the view pager
        val sliderRunnable = Runnable {
            viewPager.currentItem = viewPager.currentItem + 1
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(sliderRunnable)
                handler.postDelayed(sliderRunnable, 2000)
            }
        })

// Start auto-scroll after a delay
        handler.postDelayed(sliderRunnable, 2000)

// Center the first item initially
        viewPager

    }
}

@BindingAdapter("casts","castType")
fun bindCastAvatarRecyclerview(recyclerView: RecyclerView, casts: List<Cast>?, type: CastType){
    val adapter = when(type) {
        CastType.AVATAR -> {
            (recyclerView.adapter as MovieCastAvatarItemAdapter)
        }
        CastType.DETAIL -> {
            (recyclerView.adapter as MovieCastItemAdapter)
        }
    }
    recyclerView.apply {
        setHasFixedSize(true)
        setAdapter(adapter)
    }

    adapter.submitList(casts)
}