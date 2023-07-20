package com.kyawzinlinn.moviesapp.presentation.binding_adapter

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
import com.kyawzinlinn.moviesapp.R
import com.kyawzinlinn.moviesapp.data.local.database.MovieSearchHistory
import com.kyawzinlinn.moviesapp.data.remote.dto.Cast
import com.kyawzinlinn.moviesapp.data.remote.dto.CastDetailsDto
import com.kyawzinlinn.moviesapp.data.remote.dto.Genre
import com.kyawzinlinn.moviesapp.data.remote.dto.Movie
import com.kyawzinlinn.moviesapp.data.remote.dto.Profile
import com.kyawzinlinn.moviesapp.data.remote.dto.TrailerMovie
import com.kyawzinlinn.moviesapp.presentation.adapter.GenreAdapter
import com.kyawzinlinn.moviesapp.presentation.adapter.HorizontalMovieItemAdapter
import com.kyawzinlinn.moviesapp.presentation.adapter.TrailerItemAdapter
import com.kyawzinlinn.moviesapp.presentation.adapter.ImageSliderAdapter
import com.kyawzinlinn.moviesapp.presentation.adapter.MovieCastAvatarItemAdapter
import com.kyawzinlinn.moviesapp.presentation.adapter.MovieCastItemAdapter
import com.kyawzinlinn.moviesapp.presentation.adapter.SearchHistoryAdapter
import com.kyawzinlinn.moviesapp.presentation.adapter.VerticalMovieItemAdapter
import com.kyawzinlinn.moviesapp.presentation.custom_view.SliderDotIndicator
import com.kyawzinlinn.moviesapp.presentation.item_animator.CustomItemAnimator
import com.kyawzinlinn.moviesapp.utils.CastType
import com.kyawzinlinn.moviesapp.utils.IMG_URL_PREFIX_300
import com.kyawzinlinn.moviesapp.utils.IMG_URL_PREFIX_500
import com.kyawzinlinn.moviesapp.utils.PosterSize
import com.kyawzinlinn.moviesapp.utils.RecyclerviewType
import com.kyawzinlinn.moviesapp.utils.calculateAge

const val TAG = "TAG"

@BindingAdapter("movies", "recyclerViewType")
fun bindRecyclerView(recyclerView: RecyclerView, movies: List<Any>?, type: RecyclerviewType){
    recyclerView.itemAnimator = CustomItemAnimator()
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
    imgUrl?.let {
        imageView.load(url){
            error(R.drawable.no_image_place_holder)
            crossfade(true)
        }
    }
}

@BindingAdapter("youTubeThumbnail")
fun bindYouTubeThumbnail(imageView: ImageView, videoKey: String){
    val thumbnailUrl = "https://img.youtube.com/vi/${videoKey}/hqdefault.jpg"
    imageView.load(thumbnailUrl){crossfade(true)}
}

@BindingAdapter("setAge")
fun bindCastAge(textView: TextView, castDetailsDto: CastDetailsDto?){
    try {
        val birthday = castDetailsDto?.birthday.toString()
        val deathDay = castDetailsDto?.deathday.toString().trim()
        
        if (deathDay == "null") textView.text = "$birthday (${calculateAge(birthday)})"
        else textView.text = "$birthday (Died at $deathDay)"
    }catch (e: Exception){
        textView.text = "-"
    }
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
        val sliderAdapter = ImageSliderAdapter(profiles.map { it.file_path })
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
            // infinite auto scroll
            if (viewPager.currentItem == profiles.size - 1) viewPager.currentItem = 0
            else viewPager.currentItem = viewPager.currentItem + 1

        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(sliderRunnable)
                handler.postDelayed(sliderRunnable, 1500)
            }
        })

        // Start auto-scroll after a delay
        handler.postDelayed(sliderRunnable, 1500)

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

@BindingAdapter("dot_size")
fun bindDotSize(sliderDotIndicator: SliderDotIndicator, dotSize: Int?){
    sliderDotIndicator.setTotalDots(dotSize!!)
}

@BindingAdapter("trailers")
fun bindTrailersRecyclerview(recyclerView: RecyclerView, trailers: List<TrailerMovie>?){
    val adapter = recyclerView.adapter as TrailerItemAdapter
    recyclerView.adapter = adapter
    adapter.submitList(trailers)
}

@BindingAdapter("setText")
fun TextView.bindTexToTextView(string: String?){
    text = string.takeUnless { it.isNullOrEmpty() || it.equals("null", ignoreCase = true) } ?: "-"
}