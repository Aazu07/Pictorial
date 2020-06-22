package com.aazu.pictorial.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MediaItemDecoration(
    private val context: Context,
    private val padding: Int,
    private val spanCount: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val resources = context.resources
        val spacingPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            padding.toFloat(),
            resources.displayMetrics
        )

        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

        outRect.top = if (itemPosition < spanCount) 0 else spacingPx.toInt()
        outRect.bottom = 0

        val rowPosition = itemPosition % spanCount
        outRect.left = spacingPx.toInt()
        outRect.right = if ((spanCount - 1) == rowPosition) spacingPx.toInt() else 0

    }
}
