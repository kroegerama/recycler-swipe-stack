package com.kroegerama.reswista

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setupStack(
    config: SwiperConfig,
    listener: SwipeListener,
    block: (RecyclerView.() -> Unit)? = null
) {
    itemAnimator = null
    layoutManager = StackLayoutManager(config)
    ItemTouchHelper(StackSwipeTouchHelperCallback(listener, config)).attachToRecyclerView(this)
    block?.invoke(this)
}
