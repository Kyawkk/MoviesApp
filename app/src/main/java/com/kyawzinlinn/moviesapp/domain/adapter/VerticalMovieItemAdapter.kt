package com.kyawzinlinn.moviesapp.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kyawzinlinn.moviesapp.data.remote.dto.Movie
import com.kyawzinlinn.moviesapp.databinding.VerticalMovieItemBinding

class VerticalMovieItemAdapter(private val movies: MutableList<Movie>, private val onItemClick: (String) -> Unit): RecyclerView.Adapter< VerticalMovieItemAdapter.ViewHolder>() {

    class ViewHolder(private val binding: VerticalMovieItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie){
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    fun addMovies(newMovies: List<Movie>){

        try {
            newMovies.forEachIndexed {index, movie ->
                movies.add(movie)
                notifyItemInserted(movies.size + index + 1)
            }
        }catch (e: Exception){}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(VerticalMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies.get(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener { onItemClick(movie.id.toString()) }
    }

    override fun getItemCount() = movies.size
}