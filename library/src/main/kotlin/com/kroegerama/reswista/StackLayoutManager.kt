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
                        layout.scaleAndTranslateForPosition(pos - 1)
                    }
                    pos > 0 -> {
                        layout.scaleAndTranslateForPosition(pos)
                    }
                    pos == 0 -> {
                        layout.resetTransitions()
                    }
                }
            }
        } else {
            for (pos in itemCount - 1 downTo 0) {
                val layout = recycler.getViewForPosition(pos)
                addAndMeasureView(layout)

                when {
                    pos > 0 -> {
                        layout.scaleAndTranslateForPosition(pos)
                    }
                    pos == 0 -> {
                        layout.resetTransitions()
                    }
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

    private fun View.resetTransitions() {
        clearAnimation()
        scale(1f)
        translationX = 0f
        translationY = 0f
        animate().alpha(1f)
    }

    private fun View.scaleAndTranslateForPosition(pos: Int) {
        clearAnimation()
        animate().apply {
            val scale = 1f - pos * config.itemScale
            scaleX(scale)
            scaleY(scale)
            val itemTranslate = config.itemTranslate
            when (config.stackDirection) {
                StackDirection.Left -> translationX(-pos * measuredWidth * itemTranslate)
                StackDirection.Up -> translationY(-pos * measuredHeight * itemTranslate)
                StackDirection.Right -> translationX(pos * measuredWidth * itemTranslate)
                StackDirection.Down -> translationY(pos * measuredHeight * itemTranslate)
            }
        }
    }
}
