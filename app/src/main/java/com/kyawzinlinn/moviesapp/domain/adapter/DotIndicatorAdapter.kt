package com.kyawzinlinn.moviesapp.domain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kyawzinlinn.moviesapp.R
import com.kyawzinlinn.moviesapp.databinding.DotItemBinding

class DotIndicatorAdapter: ListAdapter<String,DotIndicatorAdapter.ViewHolder>(DiffCallBack) {

    private var selectedPosition = 0
    private lateinit var context: Context
    class ViewHolder(val binding: DotItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    fun setDotSelected(position: Int){
        selectedPosition = position
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(DotItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (context != null){
            if (selectedPosition == position){
                // selected state
                holder.binding.dot.setCardBackgroundColor(context.resources.getColor(R.color.primary_color))
                //holder.binding.dot.animate().scaleX(2f).scaleY(2f).setDuration(300).start()
            }else{
                holder.binding.dot.setCardBackgroundColor(context.resources.getColor(R.color.white))
                //holder.binding.dot.animate().scaleX(1f).scaleY(1f).setDuration(300).start()
            }
        }
    }

    companion object DiffCallBack: DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}