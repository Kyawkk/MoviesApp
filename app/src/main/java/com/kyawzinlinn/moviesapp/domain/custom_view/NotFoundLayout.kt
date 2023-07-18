package com.kyawzinlinn.moviesapp.domain.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.kyawzinlinn.moviesapp.R
import com.kyawzinlinn.moviesapp.databinding.NotFoundLayoutBinding

class NotFoundLayout(context: Context?, private val attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private lateinit var binding: NotFoundLayoutBinding
    init {
        orientation = VERTICAL
        init()
    }

    fun setNotFoundLayoutTitle(title: String) {
        binding.tvNoResultTitle.text = title
    }

    fun setNotFoundLayoutDesc(desc: String) {
        binding.tvNoResultDesc.text = desc
    }

    private fun init() {
        binding = NotFoundLayoutBinding.inflate(LayoutInflater.from(context),this,true)

        context.obtainStyledAttributes(attrs, R.styleable.NotFoundLayout).let {
            binding.tvNoResultTitle.text = it.getString(R.styleable.NotFoundLayout_notFoundLayoutTitle)
            binding.tvNoResultDesc.text = it.getString(R.styleable.NotFoundLayout_notFoundLayoutDesc)
        }
    }
}