package com.kroegerama.reswista.ui

import android.animation.ArgbEvaluator
import android.graphics.Color
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFadeThrough
import com.kroegerama.kaiteki.baseui.ViewBindingFragment
import com.kroegerama.kaiteki.onClick
import com.kroegerama.kaiteki.postponeUntilLayout
import com.kroegerama.kaiteki.recyclerview.ViewBindingBaseViewHolder
import com.kroegerama.reswista.R
import com.kroegerama.reswista.StackDirection
import com.kroegerama.reswista.SwipeDirection
import com.kroegerama.reswista.SwipeListener
import com.kroegerama.reswista.SwiperConfig
import com.kroegerama.reswista.databinding.FragStartBinding
import com.kroegerama.reswista.setupStack
import com.kroegerama.reswista.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FragStart : ViewBindingFragment<FragStartBinding>(
    FragStartBinding::inflate
) {

    private val viewModel by activityViewModels<MainViewModel>()

    private val itemAdapter by lazy { ItemAdapter() }

    override fun prepare() {
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun FragStartBinding.setupGUI() {
        postponeUntilLayout(recycler)

        val config = SwiperConfig(
            showCount = 5,
            swipeDirections = SwipeDirection.LEFT or SwipeDirection.RIGHT or SwipeDirection.UP,
            stackDirection = StackDirection.Down,
            itemTranslate = .03f,
            itemRotation = 15f,
            itemScale = .03f
        )
        recycler.setupStack(
            config,
            listener
        ) {
            adapter = ConcatAdapter(
                itemAdapter,
                EndAdapter(::refill)
            )
        }
        refill()

        var idx = 100
        btnAdd.onClick {
            itemAdapter.add(0, ColorString("Test ${idx++}", Color.MAGENTA))
        }
    }

    private fun refill() {
        val startColor = requireContext().getColor(R.color.primary)
        val endColor = requireContext().getColor(R.color.secondary)
        val evaluator = ArgbEvaluator()
        val count = 3

        fun getColor(pos: Int): Int {
            val fraction = (pos - 1f) / count
            return evaluator.evaluate(fraction, startColor, endColor) as Int
        }
        itemAdapter.setItems((1..count).map { pos -> ColorString("Card $pos", getColor(pos)) })
    }

    private val listener = object : SwipeListener {
        override fun onSwipe(
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            direction: Int
        ) {
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            Timber.d("onSwiped($direction)")
            itemAdapter.removeAt(viewHolder.bindingAdapterPosition)
        }

        override fun onSwipeEnd(viewHolder: RecyclerView.ViewHolder) {
            Timber.d("onSwipeEnd()")
        }

        override fun isSwipeAllowed(viewHolder: RecyclerView.ViewHolder) = viewHolder is ViewBindingBaseViewHolder<*>
    }
}
