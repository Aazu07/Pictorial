package com.aazu.pictorial.utils

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


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

        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val spanIndex =
            (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex

        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

        outRect.top = if (position < spanCount) 0 else spacingPx.toInt() * 2
        outRect.bottom = 0

        val rowPosition = spanIndex % spanCount
        outRect.left = spacingPx.toInt()
        outRect.right = spacingPx.toInt()
        Log.d("TESTING", " Pos $position rowIndex $rowPosition && rightPadding ${outRect.right}")

    }
}
