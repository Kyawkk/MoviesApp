package com.kyawzinlinn.moviesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kyawzinlinn.moviesapp.data.remote.dto.Cast
import com.kyawzinlinn.moviesapp.databinding.MovieCastItemBinding

class MovieCastAvatarItemAdapter(private val onCastClick: (String) -> Unit): ListAdapter<Cast, MovieCastAvatarItemAdapter.ViewHolder>(
    DiffCallBack
) {
    class ViewHolder(private val binding: MovieCastItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast){
            binding.cast = cast
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MovieCastItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cast = getItem(position)
        holder.bind(cast)
        holder.itemView.setOnClickListener { onCastClick(cast.id.toString()) }
    }

    companion object DiffCallBack: DiffUtil.ItemCallback<Cast>(){
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.name == newItem.name
        }

    }
}