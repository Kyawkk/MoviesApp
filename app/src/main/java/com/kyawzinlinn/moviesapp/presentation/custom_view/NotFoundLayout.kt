package com.kyawzinlinn.moviesapp.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.kyawzinlinn.moviesapp.R
import com.kyawzinlinn.moviesapp.databinding.NotFoundLayoutBinding

class NotFoundLayout(context: Context?, private val attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private lateinit var binding: NotFoundLayoutBinding
    private var isImageHidden = false
    private var isButtonHidden = false

    init {
        orientation = VERTICAL
        visibility = View.GONE
        init()
    }

    fun setNotFoundLayoutTitle(title: String) {
        binding.tvNoResultTitle.text = title
    }

    fun setNotFoundLayoutDesc(desc: String) {
        binding.tvNoResultDesc.text = desc
    }

    fun setOnTryAgainListener(onClick: () -> Unit){
        binding.btnAction.setOnClickListener { onClick() }
    }

    private fun init() {
        binding = NotFoundLayoutBinding.inflate(LayoutInflater.from(context),this,true)

        context.obtainStyledAttributes(attrs, R.styleable.NotFoundLayout).let {
            binding.tvNoResultTitle.text = it.getString(R.styleable.NotFoundLayout_notFoundLayoutTitle)
            binding.tvNoResultDesc.text = it.getString(R.styleable.NotFoundLayout_notFoundLayoutDesc)
            isImageHidden = it.getBoolean(R.styleable.NotFoundLayout_hideImage,false)
            isButtonHidden = it.getBoolean(R.styleable.NotFoundLayout_hideButton,false)

            binding.btnAction.visibility = if (isButtonHidden) View.GONE else View.VISIBLE
            binding.ivNoResult.visibility = if(isImageHidden) View.GONE else View.VISIBLE
        }
    }
}