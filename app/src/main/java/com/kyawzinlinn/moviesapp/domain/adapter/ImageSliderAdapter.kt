package com.kyawzinlinn.moviesapp.domain.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kyawzinlinn.moviesapp.databinding.ImageSliderItemBinding
import com.kyawzinlinn.moviesapp.utils.IMG_URL_PREFIX_300

class ImageSliderAdapter(private val images: List<String>): RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ImageSliderItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(imgUrl: String){
            Log.d("TAG", "blah: $IMG_URL_PREFIX_300$imgUrl")
            binding.imgUrl = imgUrl
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ImageSliderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images.get(position))
    }

    override fun getItemCount(): Int = images.size
}