package com.kroegerama.reswista

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kroegerama.kaiteki.scale
import kotlin.math.absoluteValue

class StackSwipeTouchHelperCallback(
    private val listener: SwipeListener,
    private val config: SwiperConfig
) : ItemTouchHelper.Callback() {

    var allowSwipe = true

    override fun isItemViewSwipeEnabled() = allowSwipe

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) = when {
        recyclerView.layoutManager !is StackLayoutManager -> 0
        recyclerView.isAnimating -> 0
        !listener.isSwipeAllowed(viewHolder) -> 0
        else -> config.swipeDirections
    }.let { swipeFlags ->
        makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = 0.3f

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val ratioX = (dX / recyclerView.xThreshold).coerceIn(-1f, 1f)
        val ratio: Float
        val direction: Int
        if (dX.absoluteValue > dY.absoluteValue) {
            ratio = ratioX
            direction = if (dX > 0) {
                SwipeDirection.RIGHT
            } else {
                SwipeDirection.LEFT
            }
        } else {
            ratio = (dY / recyclerView.yThreshold).coerceIn(-1f, 1f)
            direction = if (dY > 0) {
                SwipeDirection.DOWN
            } else {
                SwipeDirection.UP
            }
        }
        itemView.rotation = ratioX * config.itemRotation

        val childCount = recyclerView.childCount
        val start = if (childCount > config.showCount) 1 else 0

        for (pos in start until childCount - 1) {
            val idx = childCount - pos - 1
            val view = recyclerView.getChildAt(pos)
            view.scaleForPosition(idx, ratio)
            view.translateForPosition(idx, ratio)
        }
        listener.onSwipe(viewHolder, dX, dY, direction)
    }

    private fun View.scaleForPosition(pos: Int, ratio: Float) {
        scale(1 - pos * config.itemScale + ratio.absoluteValue * config.itemScale)
    }

    private fun View.translateForPosition(pos: Int, ratio: Float) {
        val translation = config.itemTranslate
        when (config.stackDirection) {
            StackDirection.Left -> translationX = -(pos - ratio.absoluteValue) * measuredWidth / translation
            StackDirection.Up -> translationY = -(pos - ratio.absoluteValue) * measuredHeight / translation
            StackDirection.Right -> translationX = (pos - ratio.absoluteValue) * measuredWidth / translation
            StackDirection.Down -> translationY = (pos - ratio.absoluteValue) * measuredHeight / translation
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.rotation = 0f
        listener.onSwipeEnd(viewHolder)
    }

    private val RecyclerView.xThreshold get() = width * .4f
    private val RecyclerView.yThreshold get() = xThreshold
}
