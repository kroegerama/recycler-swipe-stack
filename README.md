# Recycler Swipe Stack

**An easy to use helper for RecyclerViews. StackLayoutManager and ItemTouchHelperCallback.**

---


![Publish](https://github.com/kroegerama/recycler-swipe-stack/workflows/Publish/badge.svg)

![License](https://img.shields.io/github/license/kroegerama/recycler-swipe-stack)

| Artifact | Version |
|:-|:-:|
| recycler-swipe-stack | [![Maven Central](https://img.shields.io/maven-central/v/com.kroegerama/recycler-swipe-stack)](https://search.maven.org/artifact/com.kroegerama/recycler-swipe-stack) |

```kotlin
implementation("com.kroegerama:recycler-swipe-stack:$VERSION")
```

---

##### Preview

![Preview](design/preview.gif)

##### Usage

###### 1. Create your `SwiperConfig`

```kotlin
val config = SwiperConfig(
    showCount = 5,
    swipeDirections = SwipeDirection.LEFT or SwipeDirection.RIGHT or SwipeDirection.UP,
    stackDirection = StackDirection.Down,
    itemTranslate = 20.dpToPx(),
    itemRotation = 15f,
    itemScale = .0f
)
```

###### 2. Create a `SwipeListener`

```kotlin
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
        //example: remove item from Adapter (and call notifyItemRemoved,
        //if your adapter doesn't do it automatically)
        itemAdapter.removeAt(viewHolder.bindingAdapterPosition)
    }

    override fun onSwipeEnd(viewHolder: RecyclerView.ViewHolder) {
        Timber.d("onSwipeEnd()")
    }

    override fun isSwipeAllowed(viewHolder: RecyclerView.ViewHolder) = viewHolder is ViewBindingBaseViewHolder<*, *>
}
```

###### 3. Create an `ItemTouchHelper` and a `StackLayoutManager`

```kotlin
val itemTouchHelper = ItemTouchHelper(StackSwipeTouchHelperCallback(listener, config))
val layoutManager = StackLayoutManager(config)
```

###### 4. Setup your `RecyclerView`

```kotlin
recycler.layoutManager = layoutManager
itemTouchHelper.attachToRecyclerView(recycler)
```

