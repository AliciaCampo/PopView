package com.example.popview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        // Espaciado general
        outRect.left = spacing / 2
        outRect.right = spacing / 2

        // Mayor espaciado en los extremos
        if (position == 0) {
            outRect.left = spacing
        }
        if (position == itemCount - 1) {
            outRect.right = spacing
        }
    }
}