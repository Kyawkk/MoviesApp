package com.kyawzinlinn.moviesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kyawzinlinn.moviesapp.data.remote.dto.Genre
import com.kyawzinlinn.moviesapp.databinding.GenreItemBinding

class GenreAdapter(private val onGenreClick: (Genre) -> Unit): ListAdapter<Genre, GenreAdapter.ViewHolder>(
    DiffCallBack
) {
    class ViewHolder(private val binding: GenreItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre){
            binding.genre = genre
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(GenreItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener { onGenreClick(getItem(position)) }
    }


    companion object DiffCallBack: DiffUtil.ItemCallback<Genre>(){
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.name == newItem.name
        }

    }
}