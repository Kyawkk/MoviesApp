package com.kyawzinlinn.moviesapp.presentation.custom_view

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.kyawzinlinn.moviesapp.R

class DotCircle(context: Context?) : View(context) {
    private var circleColor = Color.WHITE
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    fun setCircleColor(c: Int){
        val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), circleColor, c)
        colorAnimator.duration = 200 // Animation duration in milliseconds
        colorAnimator.addUpdateListener { animator ->
            circleColor = animator.animatedValue as Int
            circlePaint.color = circleColor
            invalidate() // Redraw the view
        }
        colorAnimator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        val centerX = width / 2f
        val centerY = height / 2f

        canvas?.drawCircle(centerX,centerY,width/2f,circlePaint)
    }
}