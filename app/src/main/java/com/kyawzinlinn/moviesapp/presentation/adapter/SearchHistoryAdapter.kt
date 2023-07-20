package com.kyawzinlinn.moviesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kyawzinlinn.moviesapp.data.local.database.MovieSearchHistory
import com.kyawzinlinn.moviesapp.databinding.SearchHistoryItemBinding

class SearchHistoryAdapter(private val onSearchHistoryClick: (MovieSearchHistory) -> Unit): ListAdapter<MovieSearchHistory, SearchHistoryAdapter.ViewHolder>(
    DiffCallBack
) {
    class ViewHolder(val binding: SearchHistoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(searchHistory: MovieSearchHistory){
            binding.searchHistory = searchHistory
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SearchHistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {

            onSearchHistoryClick(getItem(position))
        }
    }


    companion object DiffCallBack: DiffUtil.ItemCallback<MovieSearchHistory>(){
        override fun areItemsTheSame(oldItem: MovieSearchHistory, newItem: MovieSearchHistory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieSearchHistory, newItem: MovieSearchHistory): Boolean {
            return oldItem.name == newItem.name
        }

    }
}