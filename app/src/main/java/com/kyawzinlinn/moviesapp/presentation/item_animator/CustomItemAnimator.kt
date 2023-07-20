package com.kyawzinlinn.moviesapp.presentation.item_animator

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class CustomItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.itemView?.alpha = 0f
        holder?.itemView?.animate()?.alpha(1f)?.start()
        return super.animateAdd(holder)
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.itemView?.animate()?.alpha(0f)?.withEndAction {
            super.animateRemove(holder)
        }?.start()
        return false
    }
}
