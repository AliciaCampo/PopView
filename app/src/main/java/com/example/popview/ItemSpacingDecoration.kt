package com.example.popview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        // Espaciado general (izquierda, derecha, arriba, abajo)
        outRect.left = spacing / 2
        outRect.right = spacing / 2
        outRect.top = spacing / 2
        outRect.bottom = spacing / 2

        // Mayor espaciado en los bordes (izquierda y derecha)
        if (position == 0) { // Primer elemento
            outRect.left = spacing
        }
        if (position == itemCount - 1) { // Último elemento
            outRect.right = spacing
        }

        // Si deseas mayor espaciado en el primer o último item en las filas de un GridLayout (si usas GridLayoutManager)
        // puedes agregar un espaciado extra superior e inferior.
        if (position == 0) {
            outRect.top = spacing
        }
        if (position == itemCount - 1) {
            outRect.bottom = spacing
        }
    }
}
