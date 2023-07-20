package com.kyawzinlinn.moviesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kyawzinlinn.moviesapp.data.remote.dto.TrailerMovie
import com.kyawzinlinn.moviesapp.databinding.HorizontalTrailerItemBinding
import com.kyawzinlinn.moviesapp.databinding.VerticalTrailerItemBinding
import com.kyawzinlinn.moviesapp.utils.RecyclerviewType

class TrailerItemAdapter(private val recyclerviewType: RecyclerviewType, private val onVideoClicked: (String) -> Unit): ListAdapter<TrailerMovie, RecyclerView.ViewHolder>(
    DiffCallBack
) {
    class HorizontalViewHolder(private val binding: HorizontalTrailerItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(trailerMovie: TrailerMovie){
            binding.videoKey = trailerMovie.key
            binding.executePendingBindings()
        }

    }

    class VerticalViewHolder(private val binding: VerticalTrailerItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(trailerMovie: TrailerMovie){
            binding.trailer = trailerMovie
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(recyclerviewType){
            RecyclerviewType.HORIZONTAL -> HorizontalViewHolder(HorizontalTrailerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            RecyclerviewType.VERTICAL -> VerticalViewHolder(VerticalTrailerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val trailer = getItem(position)

        when(recyclerviewType){
            RecyclerviewType.HORIZONTAL -> (holder as HorizontalViewHolder).bind(trailer)
            RecyclerviewType.VERTICAL -> (holder as VerticalViewHolder).bind(trailer)
        }
        holder.itemView.setOnClickListener { onVideoClicked(getItem(position).key) }
    }

    companion object DiffCallBack: DiffUtil.ItemCallback<TrailerMovie>(){
        override fun areItemsTheSame(oldItem: TrailerMovie, newItem: TrailerMovie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TrailerMovie, newItem: TrailerMovie): Boolean {
            return oldItem.key == newItem.key
        }

    }
}