package com.kroegerama.reswista

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kroegerama.kaiteki.scale

class StackLayoutManager(
    private val config: SwiperConfig = SwiperConfig()
) : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val showCount = config.showCount

        if (itemCount > showCount) {
            for (pos in showCount downTo 0) {
                val layout = recycler.getViewForPosition(pos)
                addAndMeasureView(layout)

                when {
                    pos == showCount -> {
                        layout.scaleForPosition(pos - 1)
                        layout.translateForPosition(pos - 1)
                    }
                    pos > 0 -> {
                        layout.scaleForPosition(pos)
                        layout.translateForPosition(pos)
                    }
                }
            }
        } else {
            for (pos in itemCount - 1 downTo 0) {
                val layout = recycler.getViewForPosition(pos)
                addAndMeasureView(layout)

                if (pos > 0) {
                    layout.scaleForPosition(pos)
                    layout.translateForPosition(pos)
                }
            }
        }
    }

    private fun addAndMeasureView(view: View) {
        addView(view)
        measureChildWithMargins(view, 0, 0)
        val decoratedMeasuredWidth = getDecoratedMeasuredWidth(view)
        val decoratedMeasuredHeight = getDecoratedMeasuredHeight(view)
        val widthSpace = (width - decoratedMeasuredWidth) / 2
        val heightSpace = (height - decoratedMeasuredHeight) / 2
        layoutDecoratedWithMargins(
            view,
            widthSpace, heightSpace,
            widthSpace + decoratedMeasuredWidth,
            heightSpace + decoratedMeasuredHeight
        )
    }

    private fun View.scaleForPosition(pos: Int) {
        scale(1f - pos * config.itemScale)
    }

    private fun View.translateForPosition(pos: Int) {
        val itemTranslate = config.itemTranslate
        when (config.stackDirection) {
            StackDirection.Left -> translationX = -pos * measuredWidth * itemTranslate
            StackDirection.Up -> translationY = -pos * measuredHeight * itemTranslate
            StackDirection.Right -> translationX = pos * measuredWidth * itemTranslate
            StackDirection.Down -> translationY = pos * measuredHeight * itemTranslate
        }
    }
}
