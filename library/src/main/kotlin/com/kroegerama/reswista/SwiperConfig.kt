package com.kroegerama.reswista

import androidx.annotation.IntDef
import androidx.recyclerview.widget.ItemTouchHelper

data class SwiperConfig(
    val showCount: Int = 1,
    val itemScale: Float = 0f,
    val itemTranslate: Float = 0f,
    val itemRotation: Float = 0f,

    @SwipeDirection
    val swipeDirections: Int = SwipeDirection.ALL,

    val stackDirection: StackDirection = StackDirection.Down
)

@Retention(AnnotationRetention.SOURCE)
@IntDef(SwipeDirection.LEFT, SwipeDirection.UP, SwipeDirection.RIGHT, SwipeDirection.DOWN, flag = true)
annotation class SwipeDirection {
    companion object {
        const val LEFT = ItemTouchHelper.LEFT
        const val UP = ItemTouchHelper.UP
        const val RIGHT = ItemTouchHelper.RIGHT
        const val DOWN = ItemTouchHelper.DOWN

        const val ALL = LEFT or UP or RIGHT or DOWN
    }
}

enum class StackDirection {
    Left, Up, Right, Down
}
