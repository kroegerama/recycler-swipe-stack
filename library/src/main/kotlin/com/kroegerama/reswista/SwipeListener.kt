package com.kroegerama.reswista

import androidx.recyclerview.widget.RecyclerView

interface SwipeListener {

    fun onSwipe(
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        @SwipeDirection direction: Int
    )
    fun onSwiped(viewHolder: RecyclerView.ViewHolder, @SwipeDirection direction: Int)
    fun onSwipeEnd(viewHolder: RecyclerView.ViewHolder)
    fun isSwipeAllowed(viewHolder: RecyclerView.ViewHolder): Boolean
}
