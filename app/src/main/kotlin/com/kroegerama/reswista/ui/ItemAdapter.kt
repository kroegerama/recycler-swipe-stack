package com.kroegerama.reswista.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.ColorInt
import com.kroegerama.kaiteki.recyclerview.ViewBindingBaseAdapter
import com.kroegerama.kaiteki.recyclerview.ViewBindingBaseViewHolder
import com.kroegerama.reswista.databinding.ItemCardBinding

class ItemAdapter : ViewBindingBaseAdapter<ColorString, ItemCardBinding>(
    ItemCardBinding::inflate
) {

    override fun compareItems(checkContent: Boolean, a: ColorString, b: ColorString) =
        if (checkContent) a == b else a.text == b.text

    @SuppressLint("ClickableViewAccessibility")
    override fun ItemCardBinding.prepare() {
        // swipe only allowed on card view
        root.setOnTouchListener { _, _ ->
            root.requestDisallowInterceptTouchEvent(true)
            false
        }
    }

    override fun ItemCardBinding.update(
        viewHolder: ViewBindingBaseViewHolder<ItemCardBinding>,
        context: Context,
        viewType: Int,
        item: ColorString?
    ) {
        item ?: return
        with(viewHolder.binding) {
            card.setCardBackgroundColor(item.color)
            tvText.text = item.text
        }
    }
}

data class ColorString(
    val text: String,
    @ColorInt val color: Int
)
