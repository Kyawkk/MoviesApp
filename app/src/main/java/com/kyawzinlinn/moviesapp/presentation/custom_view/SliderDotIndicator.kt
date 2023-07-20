package com.kyawzinlinn.moviesapp.presentation.custom_view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.kyawzinlinn.moviesapp.R

class SliderDotIndicator(context: Context?,private val attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private var totalDots: Int = 0
    private val dots : ArrayList<DotCircle> = ArrayList()
    private var selectedPosition = 0

    private val selectedColor = resources.getColor(R.color.primary_color)
    private val unSelectedColor = Color.WHITE

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    fun setTotalDots(dotSize: Int){
        totalDots = dotSize
        setDotIndicator()
    }

    private fun setDotIndicator(){
        removeAllViews()
        dots.clear()

        for (i in 0 until totalDots){
            addDotIndicator(i == selectedPosition)
        }
    }

    fun setSelectedDot(position: Int){
        selectedPosition = position
        for (i in 0 until dots.size){
            if (selectedPosition == i) {
                dots.get(i).setCircleColor(selectedColor)
            }
            else dots.get(i).setCircleColor(unSelectedColor)
        }
    }

    private fun addDotIndicator(isSelected: Boolean) {
        val dotCircle = DotCircle(context)
        val margin = resources.getDimensionPixelSize(R.dimen.dot_margin)
        val dotSize = resources.getDimensionPixelSize(R.dimen.dot_size)
        dotCircle.setCircleColor(if (isSelected) selectedColor else unSelectedColor)
        val dotParams = LayoutParams(dotSize,dotSize)
        dotParams.setMargins(margin,0,margin,0)
        dotCircle.layoutParams = dotParams

        dots.add(dotCircle)
        addView(dotCircle)

    }
}