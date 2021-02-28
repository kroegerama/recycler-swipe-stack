package com.kroegerama.reswista

import androidx.annotation.IntDef
import androidx.recyclerview.widget.ItemTouchHelper
import com.kroegerama.kaiteki.dpToPx

data class SwiperConfig(
    val showCount: Int = 5,
    val itemScale: Float = 0.01f,
    val itemTranslate: Int = 20.dpToPx(),
    val itemRotation: Float = 15f,

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
