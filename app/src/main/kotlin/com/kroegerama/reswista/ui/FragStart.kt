package com.kroegerama.reswista.ui

import android.animation.ArgbEvaluator
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kroegerama.kaiteki.baseui.ViewBindingFragment
import com.kroegerama.kaiteki.dpToPx
import com.kroegerama.kaiteki.recyclerview.ViewBindingBaseViewHolder
import com.kroegerama.reswista.R
import com.kroegerama.reswista.StackDirection
import com.kroegerama.reswista.StackLayoutManager
import com.kroegerama.reswista.StackSwipeTouchHelperCallback
import com.kroegerama.reswista.SwipeDirection
import com.kroegerama.reswista.SwipeListener
import com.kroegerama.reswista.SwiperConfig
import com.kroegerama.reswista.databinding.FragStartBinding
import com.kroegerama.reswista.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FragStart : ViewBindingFragment<FragStartBinding>(
    FragStartBinding::inflate
) {

    private val viewModel by activityViewModels<MainViewModel>()

    private val itemAdapter by lazy { ItemAdapter() }

    override fun FragStartBinding.setupGUI() {
        val config = SwiperConfig(
            showCount = 5,
            swipeDirections = SwipeDirection.LEFT or SwipeDirection.RIGHT or SwipeDirection.UP,
            stackDirection = StackDirection.Down,
            itemTranslate = 20.dpToPx(),
            itemRotation = 15f,
            itemScale = .0f
        )
        val itemTouchHelper = ItemTouchHelper(StackSwipeTouchHelperCallback(listener, config))
        val layoutManager = StackLayoutManager(config)
        val itemAnimator = DefaultItemAnimator().apply {
            addDuration = 200
            removeDuration = 200
        }

        recycler.layoutManager = layoutManager
        recycler.itemAnimator = itemAnimator
        itemTouchHelper.attachToRecyclerView(recycler)

        recycler.adapter = ConcatAdapter(
            itemAdapter,
            EndAdapter(::refill)
        )
        refill()
    }

    private fun refill() {
        val startColor = requireContext().getColor(R.color.primary)
        val endColor = requireContext().getColor(R.color.secondary)
        val evaluator = ArgbEvaluator()
        val count = 10

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

        override fun isSwipeAllowed(viewHolder: RecyclerView.ViewHolder) = viewHolder is ViewBindingBaseViewHolder<*, *>
    }
}